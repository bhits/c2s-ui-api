package gov.samhsa.c2s.c2suiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("fis")
public interface FisClient {
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    String getPatientResource(@RequestParam(value = "patientIdentifierSystem") String patientIdentifierSystem, @RequestParam(value = "patientIdentifierValue") String patientIdentifierValue);
}
