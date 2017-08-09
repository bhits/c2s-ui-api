package gov.samhsa.c2s.c2suiapi.service;


import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserActivationRequestDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserProfileSelfServiceEditDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserVerificationRequestDto;
import gov.samhsa.c2s.c2suiapi.service.dto.FullProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.UserDto;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Locale;

public interface UmsService {
    Object verify(UserVerificationRequestDto userVerificationRequest);

    Object checkDuplicateUsername(String username);

    Object activateUser(UserActivationRequestDto userActivationRequest,
                        String xForwardedProto,
                        String xForwardedHost,
                        String xForwardedPort);

    Object getUser(Long userId);

    Object updateUser(Long userId, UserDto userDto);

    FullProfileResponse updateUserSelfService(Long userId, UserProfileSelfServiceEditDto editUserDto);

    LimitedProfileResponse getProfile();

    FullProfileResponse getFullProfile();

    void setDefaultLocale(@RequestHeader("Accept-Language") Locale locale);
}
