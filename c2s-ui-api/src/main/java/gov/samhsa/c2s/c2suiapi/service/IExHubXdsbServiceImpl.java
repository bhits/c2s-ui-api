package gov.samhsa.c2s.c2suiapi.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
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
        catch (HystrixRuntimeException hystrixErr) {
            Throwable causedBy = hystrixErr.getCause();

            if (!(causedBy instanceof FeignException)) {
                log.error("Unexpected instance of HystrixRuntimeException has occurred", hystrixErr);
                throw new IExhubXdsbClientException("An unknown error occurred while attempting to communicate with IExHubXdsb service");
            }

            int causedByStatus = ((FeignException) causedBy).status();

            switch (causedByStatus) {
                case 404:
                    log.debug("IExHubXdsb client returned a 404 - NOT FOUND status, indicating no medical documents were found for the specified patientMrn", causedBy);
                    throw new NoDocumentsFoundException("No medical documents found for the specified patient");
                default:
                    log.error("IExHubXdsb client returned an unexpected instance of FeignException", causedBy);
                    throw new IExhubXdsbClientException("An unknown error occurred while attempting to communicate with IExHubXdsb");
            }
        }
    }
}
