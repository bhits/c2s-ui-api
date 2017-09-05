package gov.samhsa.c2s.c2suiapi.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import gov.samhsa.c2s.c2suiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.c2suiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.c2suiapi.service.dto.UserDto;
import gov.samhsa.c2s.c2suiapi.service.exception.PatientNotBelongToCurrentUserException;
import gov.samhsa.c2s.c2suiapi.service.exception.UserUnauthorizedException;
import gov.samhsa.c2s.c2suiapi.service.exception.checkedexceptions.AbstractUserAuthIdException;
import gov.samhsa.c2s.c2suiapi.service.exception.checkedexceptions.UserAuthIdNotFoundException;
import gov.samhsa.c2s.c2suiapi.service.exception.checkedexceptions.UserAuthIdVerificationException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UmsClientInterfaceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class EnforceUserAuthServiceImpl implements EnforceUserAuthService {
    private final UmsClient umsClient;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final ModelMapper modelMapper;

    @Autowired
    public EnforceUserAuthServiceImpl(UmsClient umsClient, JwtTokenExtractor jwtTokenExtractor, ModelMapper modelMapper) {
        this.umsClient = umsClient;
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.modelMapper = modelMapper;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void assertCurrentUserAuthorizedForMrn(String mrn) {
        // Get current user authId
        String userAuthId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);

        if (!umsClient.getAccessDecision(userAuthId, mrn).isVerified()) {
            log.warn("A user with the userAuthId of '" + userAuthId + "' attempted to access a patient with the MRN of '" + mrn +"', however this user is not authorized to access that patient's information.");
            throw new PatientNotBelongToCurrentUserException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertCurrentUserMatchesUserId(Long userId) {
        // Get current user authId
        String userAuthId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        UserDto userDto;

        try {
            userDto = getUserByAuthId(userAuthId);
        } catch (AbstractUserAuthIdException e) {
            log.error("Unable to get user from UMS by userAuthId", e);
            throw new UserUnauthorizedException();
        }

        if(!Objects.equals(userDto.getId(), userId)){
            log.warn("A user with the userAuthId of '" + userAuthId + "' attempted to access a user with the userId of '" + userId +"', however this user is not authorized to access that user's information.");
            throw new UserUnauthorizedException();
        }
    }

    private UserDto getUserByAuthId(String userAuthId) throws UserAuthIdNotFoundException, UserAuthIdVerificationException {
        UserDto userDto;
        try{
            userDto = modelMapper.map(umsClient.getUserByAuthId(userAuthId), UserDto.class);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch(causedByStatus) {
                case 404:
                    log.debug("UMS client returned a 404 - NOT FOUND status, indicating no user was found for the specified userAuthId", fe);
                    throw new UserAuthIdNotFoundException("No user found for the specified user auth id");
                default:
                    log.error("UMS client returned an unexpected instance of FeignException", fe);
                    throw new UmsClientInterfaceException("An unknown error occurred while attempting to communicate with UMS service");
            }
        }
        return userDto;
    }
}
