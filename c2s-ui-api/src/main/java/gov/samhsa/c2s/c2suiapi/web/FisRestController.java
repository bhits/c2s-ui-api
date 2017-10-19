package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.service.FisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fis")
public class FisRestController {

    private final FisService fisService;

    @Autowired
    public FisRestController(FisService fisService) {
        this.fisService = fisService;
    }

    @GetMapping("/resources")
    public String getPatientHealthData(@RequestParam String patientIdentifierSystem, @RequestParam String patientIdentifierValue) {
        return fisService.getPatientResource(patientIdentifierSystem,patientIdentifierValue);
    }
}
