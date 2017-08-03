package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.PatientHealthDataDto;

public interface IExHubXdsbService {

    PatientHealthDataDto getPatientHealthData(String patientMrn);
}
