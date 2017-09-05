package gov.samhsa.c2s.c2suiapi.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import gov.samhsa.c2s.c2suiapi.infrastructure.UmsAvatarClient;
import gov.samhsa.c2s.c2suiapi.service.dto.AvatarBytesAndMetaDto;
import gov.samhsa.c2s.c2suiapi.service.dto.AvatarFileInputDto;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.InvalidAvatarInputException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UmsClientInterfaceException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UserAvatarDeleteException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UserAvatarNotFoundException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UserAvatarSaveException;
import gov.samhsa.c2s.c2suiapi.service.exception.ums.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Slf4j
public class UmsAvatarServiceImpl implements UmsAvatarService {
    private final EnforceUserAuthService enforceUserAuthService;
    private final UmsAvatarClient umsAvatarClient;

    @Autowired
    public UmsAvatarServiceImpl(EnforceUserAuthService enforceUserAuthService, UmsAvatarClient umsAvatarClient) {
        this.enforceUserAuthService = enforceUserAuthService;
        this.umsAvatarClient = umsAvatarClient;
    }

    @Override
    public Object getUserAvatar(Long userId) {
        //Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);

        try {
            return umsAvatarClient.getUserAvatar(userId);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch(causedByStatus){
                case 404:
                    log.info("No avatar was found for the user specified by user ID in the request", fe);
                    throw new UserAvatarNotFoundException("The specified user does not have an avatar");
                default:
                    log.error("UMS client returned an unexpected instance of FeignException", fe);
                    throw new UmsClientInterfaceException("An unknown error occurred while attempting to communicate with UMS service");
            }
        }
    }

    @Override
    public Object saveNewUserAvatar(Long userId, AvatarFileInputDto avatarFile) {
        //Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);

        // TODO: Add check for viruses in file via call to ClamAV antivirus scanner service

        byte[] fileContents = Base64.getDecoder().decode(avatarFile.getFileContents());

        AvatarBytesAndMetaDto avatarBytesAndMetaDto = AvatarBytesAndMetaDto.builder()
                .fileName(avatarFile.getFileName())
                .fileExtension(avatarFile.getFileExtension())
                .fileSizeBytes(avatarFile.getFileSizeBytes())
                .fileContents(fileContents)
                .build();

        try {
            return umsAvatarClient.saveNewUserAvatar(userId, avatarBytesAndMetaDto);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();

            switch(causedByStatus){
                case 400:
                    log.error("Unable to save the avatar file because one or more request parameter values were invalid", fe);
                    throw new InvalidAvatarInputException(fe.getMessage());
                case 404:
                    log.error("Unable to find a user matching the user ID specified in the request", fe);
                    throw new UserNotFoundException("Unable to find the specified user");
                case 500:
                    log.error("An error occurred while attempting to save a new user avatar", fe);
                    throw new UserAvatarSaveException("An error occurred while attempting to save a new user avatar");
                default:
                    log.error("UMS client returned an unexpected instance of FeignException", fe);
                    throw new UmsClientInterfaceException("An unknown error occurred while attempting to communicate with UMS service");
            }
        }
    }

    @Override
    public void deleteUserAvatar(Long userId) {
        //Assert user ID belongs to current user
        enforceUserAuthService.assertCurrentUserMatchesUserId(userId);

        try {
            umsAvatarClient.deleteUserAvatar(userId);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch(causedByStatus){
                case 500:
                    log.error("UMS client returned an Internal Server Error exception while attempting to delete a user's avatar", fe);
                    throw new UserAvatarDeleteException("An error occurred while attempting to delete a user's avatar");
                default:
                    log.error("UMS client returned an unexpected instance of FeignException", fe);
                    throw new UmsClientInterfaceException("An unknown error occurred while attempting to communicate with UMS service");
            }
        }
    }
}
