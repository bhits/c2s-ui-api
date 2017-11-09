package gov.samhsa.c2s.c2suiapi.service;

import feign.FeignException;
import gov.samhsa.c2s.c2suiapi.infrastructure.PcmClient;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentAttestationDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentProviderDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentRevocationDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentTermDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.DetailedConsentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.PcmConsentActivityDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ProviderDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.PurposeDto;
import gov.samhsa.c2s.c2suiapi.service.dto.ConsentActivityDto;
import gov.samhsa.c2s.c2suiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.c2suiapi.service.exception.DuplicateConsentException;
import gov.samhsa.c2s.c2suiapi.service.exception.InvalidConsentSignDateException;
import gov.samhsa.c2s.c2suiapi.service.exception.PcmInterfaceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class PcmServiceImpl implements PcmService {
    private static final boolean CREATED_BY_PATIENT = true;
    private static final boolean UPDATED_BY_PATIENT = true;
    private static final boolean ATTESTED_BY_PATIENT = true;
    private static final boolean REVOKED_BY_PATIENT = true;
    private static final String DATA_SOURCE_DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss.S";
    private static final String OUTPUT_DATE_TIME_FORMATTER = "MM/dd/yyyy HH:mm:ss";
    private final PcmClient pcmClient;
    private final EnforceUserAuthService enforceUserAuthService;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final ModelMapper modelMapper;

    @Autowired
    public PcmServiceImpl(PcmClient pcmClient, EnforceUserAuthService enforceUserAuthService, JwtTokenExtractor jwtTokenExtractor, ModelMapper modelMapper) {
        this.pcmClient = pcmClient;
        this.enforceUserAuthService = enforceUserAuthService;
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ConsentProviderDto> getProviders(String mrn) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        return pcmClient.getProviders(mrn);
    }

    @Override
    public void saveProviders(String mrn, Set<ProviderDto> providers) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        pcmClient.saveProviders(mrn, providers);
    }

    @Override
    public void deleteProvider(String mrn, Long providerId) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        pcmClient.deleteProvider(mrn, providerId);
    }

    @Override
    public Object getConsent(String mrn, Long consentId, String format) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        return pcmClient.getConsent(mrn, consentId, format);
    }

    @Override
    public Object getAttestedConsent(String mrn, Long consentId, String format) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        return pcmClient.getAttestedConsent(mrn, consentId, format);
    }

    @Override
    public Object getRevokedConsent(String mrn, Long consentId, String format) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        return pcmClient.getRevokedConsent(mrn, consentId, format);
    }

    @Override
    public PageableDto<DetailedConsentDto> getConsents(String mrn, Integer page, Integer size) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
        return pcmClient.getConsents(mrn, page, size, LocaleContextHolder.getLocale());
    }

    @Override
    public void saveConsent(String mrn, ConsentDto consentDto) {
        try {
            //Assert mrn belong to current user
            enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);

            // Get current user authId
            String createdBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
            pcmClient.saveConsent(mrn, consentDto, LocaleContextHolder.getLocale(), createdBy, CREATED_BY_PATIENT);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch (causedByStatus) {
                case 409:
                    log.error("The specified patient already has this consent", fe);
                    throw new DuplicateConsentException("Already created same consent.");
                default:
                    log.error("Unexpected instance of FeignException has occurred", fe);
                    throw new PcmInterfaceException("An unknown error occurred while attempting to communicate with PCM service");
            }
        }
    }

    @Override
    public void deleteConsent(String mrn, Long consentId) {

        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);

        // Get current user authId
        String lastUpdatedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.deleteConsent(mrn, consentId, lastUpdatedBy);
    }

    @Override
    public void updateConsent(String mrn, Long consentId, ConsentDto consentDto) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);

        // Get current user authId
        String lastUpdatedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.updateConsent(mrn, consentId, consentDto, lastUpdatedBy, UPDATED_BY_PATIENT);
    }

    @Override
    public void attestConsent(String mrn, Long consentId, ConsentAttestationDto consentAttestationDto) {
        try {
            //Assert mrn belong to current user
            enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);
            // Get current user authId
            String attestedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
            pcmClient.attestConsent(mrn, consentId, consentAttestationDto, attestedBy, ATTESTED_BY_PATIENT);
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch (causedByStatus) {
                case 400:
                    log.error("Consent start date early than Signing date", fe);
                    throw new InvalidConsentSignDateException("Consent start date early than Signing date.");
                default:
                    log.error("Unexpected instance of FeignException has occurred", fe);
                    throw new PcmInterfaceException("An unknown error occurred while attempting to communicate with PCM service");
            }
        }
    }

    @Override
    public void revokeConsent(String mrn, Long consentId, ConsentRevocationDto consentRevocationDto) {
        //Assert mrn belong to current user
        enforceUserAuthService.assertCurrentUserAuthorizedForMrn(mrn);

        // Get current user authId
        String revokedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.revokeConsent(mrn, consentId, consentRevocationDto, revokedBy, REVOKED_BY_PATIENT);
    }

    @Override
    public List<PurposeDto> getPurposes() {
        return pcmClient.getPurposes(LocaleContextHolder.getLocale());
    }

    @Override
    public ConsentTermDto getConsentAttestationTerm(Long id) {
        return pcmClient.getConsentAttestationTerm(id, LocaleContextHolder.getLocale());
    }

    @Override
    public ConsentTermDto getConsentRevocationTerm(Long id) {
        return pcmClient.getConsentRevocationTerm(id, LocaleContextHolder.getLocale());
    }

    @Override
    public PageableDto<ConsentActivityDto> getConsentActivities(String mrn, Integer page, Integer size) {
        //Mapping of generic parameterized types
        Type pageableConsentActivityDtoType = new TypeToken<PageableDto<ConsentActivityDto>>() {
        }.getType();

        Locale selectedLocale = LocaleContextHolder.getLocale();
        PageableDto<PcmConsentActivityDto> pcmConsentActivityDtoPageableDto = pcmClient.getConsentActivities(mrn, page, size, selectedLocale);
        PageableDto<ConsentActivityDto> consentActivityDtoPageableDto = modelMapper.map(pcmConsentActivityDtoPageableDto, pageableConsentActivityDtoType);
        consentActivityDtoPageableDto.setContent(mapToConsentActivityDtoList(pcmConsentActivityDtoPageableDto));

        return consentActivityDtoPageableDto;
    }

    private List<ConsentActivityDto> mapToConsentActivityDtoList(PageableDto<PcmConsentActivityDto> pcmConsentActivityDtoPageableDto) {
        return pcmConsentActivityDtoPageableDto.getContent().stream()
                .map(pcmConsentActivityDto -> ConsentActivityDto.builder()
                        .consentReferenceId(pcmConsentActivityDto.getConsentReferenceId())
                        .actionType(pcmConsentActivityDto.getActionType())
                        .updatedBy(pcmConsentActivityDto.getUpdatedBy())
                        .updatedDateTime(formatDateTime(pcmConsentActivityDto.getUpdatedDateTime()))
                        .role(pcmConsentActivityDto.getRole().getName())
                        .build())
                .collect(toList());
    }

    private String formatDateTime(String updatedDateTime) {
        DateTimeFormatter dataSourceFormatter = DateTimeFormatter.ofPattern(DATA_SOURCE_DATE_TIME_FORMATTER);
        LocalDateTime formatterLocalDateTime = LocalDateTime.parse(updatedDateTime, dataSourceFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_TIME_FORMATTER);
        return formatterLocalDateTime.format(outputFormatter);
    }
}