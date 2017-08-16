package gov.samhsa.c2s.c2suiapi.infrastructure;

import gov.samhsa.c2s.c2suiapi.config.feignsupport.FormEncoderSupportConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "uaa", url = "${c2s.c2s-ui-api.oauth2.base-url}", configuration = FormEncoderSupportConfig.class)
public interface UaaClient {

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Object getTokenUsingPasswordGrant(Map<String, ?> requestParams);
}
