package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.PatientHealthDataDto;
import gov.samhsa.c2s.c2suiapi.service.IExHubXdsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iexhub-xdsb")
public class IExHubXdsbRestController {
    private final IExHubXdsbService iExHubXdsbService;

    @Autowired
    public IExHubXdsbRestController(IExHubXdsbService iExHubXdsbService) {
        this.iExHubXdsbService = iExHubXdsbService;
    }

    @GetMapping("/patients/{patientMrn}/health-information")
    public PatientHealthDataDto getPatientDocumentsList(@PathVariable String patientMrn) {
        return iExHubXdsbService.getPatientHealthData(patientMrn);
    }
}
