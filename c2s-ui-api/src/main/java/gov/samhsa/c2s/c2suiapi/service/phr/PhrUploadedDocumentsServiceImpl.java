package gov.samhsa.c2s.c2suiapi.service.phr;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import gov.samhsa.c2s.c2suiapi.infrastructure.TryPolicyClient;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.UploadedDocumentInfoDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.phr.PhrUploadedDocumentsClient;
import gov.samhsa.c2s.c2suiapi.service.EnforceUserAuthService;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.DocumentDeleteException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.DocumentInvalidException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.DocumentNameExistsException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.DocumentSaveException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.InvalidInputException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.NoDocumentsFoundException;
import gov.samhsa.c2s.c2suiapi.service.exception.phr.PhrClientInterfaceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PhrUploadedDocumentsServiceImpl implements PhrUploadedDocumentsService {
    private static final String SAMPLE_DOCUMENT_CONTENT_TYPE = "text/xml";
    private static final String SAMPLE_DOCUMENT_TYPE_DISPLAY_NAME = "Sample Document Type";
    private static final long SAMPLE_DOCUMENT_TYPE_CODE_ID = -1;
    private final PhrUploadedDocumentsClient phrUploadedDocumentsClient;
    private final TryPolicyClient tryPolicyClient;
    private final EnforceUserAuthService enforceUserAuthService;

    @Autowired
    public PhrUploadedDocumentsServiceImpl(PhrUploadedDocumentsClient phrUploadedDocumentsClient, EnforceUserAuthService enforceUserAuthService, TryPolicyClient tryPolicyClient) {
        this.phrUploadedDocumentsClient = phrUploadedDocumentsClient;
        this.enforceUserAuthService = enforceUserAuthService;
        this.tryPolicyClient = tryPolicyClient;
    }


    @Override
    public List<Object> getAllDocumentTypeCodesList() {
        List<Object> documentTypeCodes;

        try {
            documentTypeCodes = phrUploadedDocumentsClient.getAllDocumentTypeCodesList();
        } catch (HystrixRuntimeException hystrixErr) {
            log.error("Unexpected instance of HystrixRuntimeException has occurred: ", hystrixErr);
            throw new PhrClientInterfaceException("An unknown error occurred while attempting to communicate with PHR service");
        }

        return documentTypeCodes;
    }

    /**
     * Combine patient uploaded clinical documents and sample clinical documents provided by try my policy
     *
     * @param patientMrn
     * @return
     */
    @Override
    public Object getPatientDocumentInfoList(String patientMrn) {
        Object uploadedDocuments;

        // Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(patientMrn);

        try {
            uploadedDocuments = addSampleDocsToPatientUploadedDocumentList(patientMrn);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();

            switch (causedByStatus) {
                case 404:
                    log.debug("PHR client returned a 404 - NOT FOUND status, indicating no documents were found for the specified patientMrn", fe);
                    throw new NoDocumentsFoundException("No documents found for the specified patient");
                default:
                    log.error("PHR client returned an unexpected instance of FeignException", fe);
                    throw new PhrClientInterfaceException("An unknown error occurred while attempting to communicate with PHR service");
            }
        }

        return uploadedDocuments;
    }

    @Override
    public Object getPatientDocumentByDocId(String patientMrn, Long id) {
        Object returnedDocument;

        // Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(patientMrn);

        try {
            returnedDocument = phrUploadedDocumentsClient.getPatientDocumentByDocId(patientMrn, id);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();

            switch (causedByStatus) {
                case 404:
                    log.debug("PHR client returned a 404 - NOT FOUND status, indicating either no documents were found for the specified patientMrn," +
                            "or the document requested does not belong to the specified patientMrn", fe);
                    throw new NoDocumentsFoundException("No document found with the specified document ID");
                case 400:
                    log.error("PHR client returned a 400 - BAD REQUEST status, indicating invalid input was passed to PHR client", fe);
                    throw new InvalidInputException("Invalid input was passed to PHR client");
                default:
                    log.error("PHR client returned an unexpected instance of FeignException", fe);
                    throw new PhrClientInterfaceException("An unknown error occurred while attempting to communicate with PHR service");
            }
        }

        return returnedDocument;
    }

    @Override
    public Object saveNewPatientDocument(String patientMrn, MultipartFile file, String documentName, String description, Long documentTypeCodeId) {
        Object returnedSavedDocument;

        // Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(patientMrn);

        try {
            returnedSavedDocument = phrUploadedDocumentsClient.saveNewPatientDocument(patientMrn, file, documentName, description, documentTypeCodeId);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();

            switch (causedByStatus) {
                case 400:
                    log.error("PHR client returned a 400 - BAD REQUEST status, indicating invalid input was passed to PHR client", fe);
                    throw new InvalidInputException("Invalid input was passed to PHR client");
                case 412:
                    log.info("Document is invalid", fe);
                    throw new DocumentInvalidException();
                case 409:
                    log.info("The specified patient already has a document with the same document name", fe);
                    throw new DocumentNameExistsException("The specified patient already has a document with the same document name");
                case 500:
                    log.error("An error occurred while attempting to save a new document", fe);
                    throw new DocumentSaveException("An error occurred while attempting to save a new document");
                default:
                    log.error("PHR client returned an unexpected instance of FeignException", fe);
                    throw new PhrClientInterfaceException("An unknown error occurred while attempting to communicate with PHR service");
            }
        }

        return returnedSavedDocument;
    }

    @Override
    public void deletePatientDocument(String patientMrn, Long id) {
        // Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(patientMrn);

        try {
            phrUploadedDocumentsClient.deletePatientDocument(patientMrn, id);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();

            switch (causedByStatus) {
                case 400:
                    log.error("PHR client returned a 400 - BAD REQUEST status, indicating invalid input was passed to PHR client", fe);
                    throw new InvalidInputException("Invalid input was passed to PHR client");
                case 404:
                    log.error("No documents were found with the specified document ID", fe);
                    throw new NoDocumentsFoundException("No document found with the specified document ID");
                case 500:
                    log.error("An error occurred while attempting to delete a document", fe);
                    throw new DocumentDeleteException("An error occurred while attempting to delete a document");
                default:
                    log.error("PHR client returned an unexpected instance of FeignException", fe);
                    throw new PhrClientInterfaceException("An unknown error occurred while attempting to communicate with PHR service");
            }
        }
    }

    private Object addSampleDocsToPatientUploadedDocumentList(String patientMrn) {
        List<Object> uploadedDocumentInfoDtos = phrUploadedDocumentsClient.getPatientDocumentInfoList(patientMrn);
        List<UploadedDocumentInfoDto> sampleDocDtos = tryPolicyClient.getSampleDocuments().stream()
                .map(sampleDocDto -> UploadedDocumentInfoDto.builder()
                        .id((long) sampleDocDto.getId())
                        .isSampleDocument(sampleDocDto.isSampleDocument())
                        .documentName(sampleDocDto.getDocumentName())
                        .fileName(sampleDocDto.getFilePath())
                        .contentType(SAMPLE_DOCUMENT_CONTENT_TYPE)
                        .documentTypeCodeId(SAMPLE_DOCUMENT_TYPE_CODE_ID)
                        .documentTypeDisplayName(SAMPLE_DOCUMENT_TYPE_DISPLAY_NAME)
                        .build())
                .collect(Collectors.toList());

        return Stream.concat(uploadedDocumentInfoDtos.stream(), sampleDocDtos.stream())
                .collect(Collectors.toList());
    }
}
