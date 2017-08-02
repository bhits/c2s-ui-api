package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.service.PlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static gov.samhsa.c2s.c2suiapi.infrastructure.PlsClient.X_FORWARDED_HOST;
import static gov.samhsa.c2s.c2suiapi.infrastructure.PlsClient.X_FORWARDED_PORT;
import static gov.samhsa.c2s.c2suiapi.infrastructure.PlsClient.X_FORWARDED_PREFIX;
import static gov.samhsa.c2s.c2suiapi.infrastructure.PlsClient.X_FORWARDED_PROTO;

@RestController
@RequestMapping("/pls")
public class PlsRestController {

    @Autowired
    private PlsService plsService;

    @GetMapping("/providers/search/query")
    public Object searchProviders(
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
            @RequestHeader(X_FORWARDED_PORT) String xForwardedPort) {

        return plsService.searchProviders(state, city, zipCode, firstName, lastName, genderCode,
                orgName, phone, page, size, sort, projection, xForwardedProto, xForwardedHost, xForwardedPrefix, xForwardedPort);
    }
}