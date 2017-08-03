package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentAttestationDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentProviderDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentRevocationDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.ConsentTermDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.DetailedConsentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.PurposeDto;
import gov.samhsa.c2s.c2suiapi.service.dto.ConsentActivityDto;

import java.util.List;
import java.util.Locale;

public interface PcmService {
    List<ConsentProviderDto> getProviders(String mrn);

    void saveProviders(String mrn, IdentifiersDto providerIdentifiersDto);

    void deleteProvider(String mrn, Long providerId);

    Object getConsent(String mrn, Long consentId, String format);

    Object getAttestedConsent(String mrn, Long consentId, String format);

    Object getRevokedConsent(String mrn, Long consentId, String format);

    PageableDto<DetailedConsentDto> getConsents(String mrn, Integer page, Integer size, Locale locale);

    void saveConsent(String mrn, ConsentDto consentDto, Locale locale);

    void deleteConsent(String mrn, Long consentId);

    void updateConsent(String mrn, Long consentId, ConsentDto consentDto);

    void attestConsent(String mrn, Long consentId, ConsentAttestationDto consentAttestationDto);

    void revokeConsent(String mrn, Long consentId, ConsentRevocationDto consentRevocationDto);

    List<PurposeDto> getPurposes( Locale locale);

    ConsentTermDto getConsentAttestationTerm(Long id, Locale locale);

    ConsentTermDto getConsentRevocationTerm(Long id, Locale locale);

    PageableDto<ConsentActivityDto> getConsentActivities(String mrn, Integer page, Integer size);
}