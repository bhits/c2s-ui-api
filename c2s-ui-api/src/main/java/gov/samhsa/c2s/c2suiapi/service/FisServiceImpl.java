package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.FisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FisServiceImpl implements FisService {

    private final FisClient fisClient;

    public FisServiceImpl(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @Override
    public String getPatientResource(String patientIdentifierSystem, String patientIdentifierValue) {
        return fisClient.getPatientResource(patientIdentifierSystem, patientIdentifierValue);
    }
}
