package gov.samhsa.c2s.c2suiapi.infrastructure;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@FeignClient("ums")
public interface UmsClient {
    String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    String X_FORWARDED_HOST = "X-Forwarded-Host";
    String X_FORWARDED_PORT = "X-Forwarded-Port";

    @RequestMapping(value = "/users/verification", method = RequestMethod.POST)
    Object verify(@Valid @RequestBody UserVerificationRequestDto userVerificationRequest);

    @RequestMapping(value = "/users/activation", method = RequestMethod.GET)
    Object checkDuplicateUsername(@RequestParam("username") String username);

    @RequestMapping(value = "/users/activation", method = RequestMethod.POST)
    Object activateUser(@Valid @RequestBody UserActivationRequestDto userActivationRequest,
                        @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                        @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                        @RequestHeader(X_FORWARDED_PORT) String xForwardedPort);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    UmsUserDto getUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    UmsUserDto updateUser(@PathVariable("userId") Long userId, @RequestBody UmsUserDto umsUserDto);

    @RequestMapping(value = "/users/{userId}/limitedFields", method = RequestMethod.PUT)
    UmsUserDto updateUserLimitedFields(@PathVariable("userId") Long userId, @RequestBody UserProfileSelfServiceEditDto userProfileSelfServiceEditDto);

    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getLocales();

    @RequestMapping(value = "/users/authId/{userAuthId}", method = RequestMethod.GET)
    UmsUserDto getUserByAuthId(@PathVariable("userAuthId") String userAuthId);

    @RequestMapping(value = "/users/accessDecision", method = RequestMethod.GET)
    AccessDecisionDto getAccessDecision(@RequestParam("userAuthId") String userAuthId, @RequestParam("patientMRN") String patientMRN);

    @RequestMapping(value = "/users/locale", method = RequestMethod.PUT)
    void updateUserLocaleByUserAuthId(@RequestParam("userAuthId") String userAuthId, @RequestHeader("Accept-Language") Locale locale);

}