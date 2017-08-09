package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserActivationRequestDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserProfileSelfServiceEditDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserVerificationRequestDto;
import gov.samhsa.c2s.c2suiapi.service.dto.FullProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.c2suiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class UmsServiceImpl implements UmsService {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final UmsClient umsClient;
    private final ModelMapper modelMapper;
    private final EnforceUserAuthService enforceUserAuthService;

    @Autowired
    public UmsServiceImpl(JwtTokenExtractor jwtTokenExtractor, UmsClient umsClient, ModelMapper modelMapper, EnforceUserAuthService enforceUserAuthService) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.umsClient = umsClient;
        this.modelMapper = modelMapper;
        this.enforceUserAuthService = enforceUserAuthService;
    }

    @Override
    public Object verify(UserVerificationRequestDto userVerificationRequest) {
        return umsClient.verify(userVerificationRequest);
    }

    @Override
    public Object checkDuplicateUsername(String username) {
        return umsClient.checkDuplicateUsername(username);
    }

    @Override
    public Object activateUser(UserActivationRequestDto userActivationRequest, String xForwardedProto, String xForwardedHost, String xForwardedPort) {
        return umsClient.activateUser(userActivationRequest, xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @Override
    public Object getUser(Long userId) {
        //Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);

        return modelMapper.map(umsClient.getUser(userId), UserDto.class);
    }

    @Override
    public Object updateUser(Long userId, UserDto userDto) {
        //Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);

        return umsClient.updateUser(userId, modelMapper.map(userDto, UmsUserDto.class));
    }

    @Override
    public FullProfileResponse updateUserSelfService(Long userId, UserProfileSelfServiceEditDto editUserDto) {
        // Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);
        editUserDto.setLastUpdatedBy(jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID));

        UserDto updatedUserDto = modelMapper.map(umsClient.updateUserLimitedFields(userId, editUserDto), UserDto.class);

        return buildFullProfileResponse(updatedUserDto);
    }

    @Override
    public LimitedProfileResponse getProfile() {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();
        //Get Current user
        String userAuthId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        String currentUsername = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_NAME);
        UmsUserDto currentUser = umsClient.getUserByAuthId(userAuthId);

        return LimitedProfileResponse.builder()
                .userId(currentUser.getId())
                .userLocale(currentUser.getLocale())
                .supportedLocales(supportedLocales)
                .username(currentUsername)
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .birthDate(currentUser.getBirthDate())
                .mrn(currentUser.getMrn())
                .build();
    }

    @Override
    public FullProfileResponse getFullProfile() {
        //Get Current user
        String userAuthId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        UmsUserDto currentUser = umsClient.getUserByAuthId(userAuthId);

        UserDto userDto = modelMapper.map(currentUser, UserDto.class);

        return buildFullProfileResponse(userDto);
    }

    @Override
    public void setDefaultLocale(@RequestHeader("Accept-Language") Locale locale) {
        String userAuthId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        umsClient.updateUserLocaleByUserAuthId(userAuthId, locale);
    }

    /**
     * Builds a FullProfileResponse object from a UserDto
     * <p>
     * NOTE: The FullProfileResponse object is built using lombok's Builder method
     *
     * @param userDto - The UserDto from which to build the FullProfileResponse object
     * @return The built FullProfileResponse object
     * @see lombok.Builder
     */
    private FullProfileResponse buildFullProfileResponse(UserDto userDto) {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();

        return FullProfileResponse.builder()
                .userId(userDto.getId())
                .userAuthId(userDto.getUserAuthId())
                .userLocale(userDto.getLocale())
                .supportedLocales(supportedLocales)
                .firstName(userDto.getFirstName())
                .middleName(userDto.getMiddleName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .genderCode(userDto.getGenderCode())
                .socialSecurityNumber(userDto.getSocialSecurityNumber())
                .homeAddress(userDto.getHomeAddress())
                .homeEmail(userDto.getHomeEmail())
                .homePhone(userDto.getHomePhone())
                .roles(userDto.getRoles())
                .identifiers(userDto.getIdentifiers())
                .registrationPurposeEmail(userDto.getRegistrationPurposeEmail())
                .mrn(userDto.getMrn())
                .build();
    }
}
