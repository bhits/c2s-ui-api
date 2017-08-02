package gov.samhsa.c2s.c2suiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("pls")
public interface PlsClient {
    String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    String X_FORWARDED_HOST = "X-Forwarded-Host";
    String X_FORWARDED_PORT = "X-Forwarded-Port";
    String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";

    interface Projection {
        String FLATTEN_SMALL_PROVIDER = "FlattenSmallProvider";
    }

    @RequestMapping(value = "/providers/search/query", method = RequestMethod.GET)
    Object searchProviders(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "zipcode", required = false) String zipCode,
            @RequestParam(value = "firstname", required = false) String firstName,
            @RequestParam(value = "lastname", required = false) String lastName,
            @RequestParam(value = "gendercode", required = false) String genderCode,
            @RequestParam(value = "orgname", required = false) String orgName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "projection") String projection,
            @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
            @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
            @RequestHeader(X_FORWARDED_PREFIX) String xForwardedPrefix,
            @RequestHeader(X_FORWARDED_PORT) String xForwardedPort);
}