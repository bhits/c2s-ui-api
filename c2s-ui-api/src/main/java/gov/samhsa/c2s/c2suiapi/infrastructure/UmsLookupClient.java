package gov.samhsa.c2s.c2suiapi.infrastructure;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.IdentifierSystemDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.RoleDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;

@FeignClient("ums")
public interface UmsLookupClient {
    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getLocales();

    @RequestMapping(value = "/statecodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getStateCodes();

    @RequestMapping(value = "/countrycodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getCountryCodes();

    @RequestMapping(value = "/gendercodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getGenderCodes(@RequestHeader("Accept-Language") Locale locale);

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    List<RoleDto> getRoles(@RequestHeader("Accept-Language") Locale locale);

    @RequestMapping(value = "/identifierSystems", method = RequestMethod.GET)
    List<IdentifierSystemDto> getIdentifierSystem();
}
