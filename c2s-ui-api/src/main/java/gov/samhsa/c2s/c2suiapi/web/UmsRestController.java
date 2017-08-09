package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserActivationRequestDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserProfileSelfServiceEditDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UserVerificationRequestDto;
import gov.samhsa.c2s.c2suiapi.service.UmsServiceImpl;
import gov.samhsa.c2s.c2suiapi.service.dto.FullProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.c2suiapi.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

import static gov.samhsa.c2s.c2suiapi.infrastructure.UmsClient.X_FORWARDED_HOST;
import static gov.samhsa.c2s.c2suiapi.infrastructure.UmsClient.X_FORWARDED_PORT;
import static gov.samhsa.c2s.c2suiapi.infrastructure.UmsClient.X_FORWARDED_PROTO;

@RestController
@RequestMapping("/ums")
public class UmsRestController {

    private final UmsServiceImpl umsService;

    @Autowired
    public UmsRestController(UmsServiceImpl umsService) {
        this.umsService = umsService;
    }

    @PostMapping(value = "/users/verification")
    public Object verify(@Valid @RequestBody UserVerificationRequestDto userVerificationRequest) {
        return umsService.verify(userVerificationRequest);
    }

    @GetMapping(value = "/users/activation")
    public Object checkDuplicateUsername(@RequestParam String username) {
        return umsService.checkDuplicateUsername(username);
    }

    @GetMapping("/users/{userId}")
    public Object getUser(@PathVariable Long userId) {
        return umsService.getUser(userId);
    }

    @PutMapping("/users/{userId}")
    public Object updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        return umsService.updateUser(userId, userDto);
    }

    @PostMapping(value = "/users/activation")
    public Object activateUser(@Valid @RequestBody UserActivationRequestDto userActivationRequest,
                               @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                               @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                               @RequestHeader(X_FORWARDED_PORT) String xForwardedPort) {
        return umsService.activateUser(userActivationRequest, xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @GetMapping("/user/profile")
    public LimitedProfileResponse getLimitedProfile() {
        return umsService.getProfile();
    }

    @GetMapping("/user/fullProfile")
    public FullProfileResponse getFullProfile() {
        return umsService.getFullProfile();
    }

    @PutMapping("/users/locale")
    @ResponseStatus(HttpStatus.OK)
    public void setDefaultLocale(@RequestHeader("Accept-Language") Locale locale) {
        umsService.setDefaultLocale(locale);
    }

    @PutMapping("/self-service/users/{userId}")
    public FullProfileResponse selfServiceEditUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileSelfServiceEditDto editUserDto) {
        return umsService.updateUserSelfService(userId, editUserDto);
    }
}
