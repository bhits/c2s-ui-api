package gov.samhsa.c2s.c2suiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("iexhub-xdsb")
public interface IExHubXdsbClient {
    @RequestMapping(value = "/patients/{patientId}/health-information", method = RequestMethod.GET)
    String getPatientHealthDataFromHIE(@PathVariable("patientId") String patientId);
}
