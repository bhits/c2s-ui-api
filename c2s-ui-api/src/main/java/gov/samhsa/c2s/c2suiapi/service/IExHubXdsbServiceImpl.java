package gov.samhsa.c2s.c2suiapi.service;

import feign.FeignException;
import gov.samhsa.c2s.c2suiapi.infrastructure.IExHubXdsbClient;
import gov.samhsa.c2s.c2suiapi.service.exception.IExhubXdsbClientException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.NoDocumentsFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IExHubXdsbServiceImpl implements IExHubXdsbService {

    private final IExHubXdsbClient iexhubXdsbClient;
    private final ModelMapper modelMapper;

    public IExHubXdsbServiceImpl(IExHubXdsbClient iexhubXdsbClient, ModelMapper modelMapper) {
        this.iexhubXdsbClient = iexhubXdsbClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public Object getPatientHealthData(String patientMrn) {
        log.info("Fetching Patient Health Data from IExHubXDSB...");
        try {
            //patientId is MRN, not Patient.id
            Object jsonResponse = iexhubXdsbClient.getPatientHealthDataFromHIE(patientMrn);
            log.info("Got response from IExHubXDSB...");
            return jsonResponse;
        }
        catch (FeignException fe){
            if(fe.status() == 404){
                log.error("IExHubXdsb client returned a 404 - NOT FOUND status, indicating no medical documents were found for the specified patientMrn", fe);
                throw new NoDocumentsFoundException("No medical documents found for the specified patient");
            } else {
                log.error("IExHubXdsb client returned an unexpected instance of FeignException", fe);
                throw new IExhubXdsbClientException("An unknown error occurred while attempting to communicate with IExHubXdsb");
            }
        }
    }
}
