package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.AddressDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.AuthorDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.CdaDocumentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.ContactInfoDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.DocumentDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.PatientHealthDataDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.ProviderDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.SectionDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.TargetPatientDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.TelecommunicationDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.TreatmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Slf4j
public class IExHubXdsbServiceImpl implements IExHubXdsbService {

    @Override
    public PatientHealthDataDto getPatientHealthData(String patientMrn) {
        //Todo: Remove it when integrate with iexhub-xdsb
        log.info("Get health information for patient mrn: " + patientMrn);

        //Mock patient address
        AddressDto addressDto = AddressDto.builder()
                .type("Primary Home")
                .addressLine("1357 Amber Drive")
                .city("Richmond")
                .state("VA")
                .code("23222")
                .country("US")
                .build();

        //Mock patient telecommunication
        TelecommunicationDto telecommunication = TelecommunicationDto.builder()
                .value("(816)276-6909")
                .use("Primary Home")
                .build();

        //Mock patient contactInfo
        ContactInfoDto contactInfoDto = ContactInfoDto.builder()
                .address(addressDto)
                .telecommunications(Arrays.asList(telecommunication))
                .build();

        //Mock patient targetPatient
        TargetPatientDto targetPatient = TargetPatientDto.builder()
                .dob(LocalDate.of(1977, 5, 1))
                .gender("Female")
                .race("White")
                .ethnicity("Not Hispanic or Latino")
                .name("Sally Share")
                .contactInfo(contactInfoDto)
                .ids(Arrays.asList("940140b7-7c8b-4491-90f4-4819ded969bf^1.3.6.1.4.1.21367.13.20.3000"))
                .build();

        //Mock patient provider
        ProviderDto providerDto1 = ProviderDto.builder()
                .providerName("Dr. Nancy Nightingale")
                .organizationName("Community Health Clinic")
                .softwareUse("Software used")
                .nationalProviderId(null)
                .contactInfo(null)
                .build();
        ProviderDto providerDto2 = ProviderDto.builder()
                .providerName("Dr. Bob Daylight")
                .organizationName("Community Health Clinic")
                .softwareUse("Software used")
                .nationalProviderId(null)
                .contactInfo(null)
                .build();

        //Mock patient treatment
        TreatmentDto treatmentDto = TreatmentDto.builder()
                .service("Opioid Abuse Disorder\\nTreatment")
                .serviceStartDate(LocalDate.of(2012, 8, 6))
                .serviceEndDate(LocalDate.of(2012, 8, 13))
                .providers(Arrays.asList(providerDto1, providerDto2))
                .build();

        //Mock patient Author address
        AddressDto authorAddress = AddressDto.builder()
                .type("Work Place")
                .addressLine("Get Well Clinic Facility\\n1002 Healthcare Dr.\\n")
                .city("Richmond")
                .state("VA")
                .code("23222")
                .country("United States of America")
                .build();

        //Mock patient Author telecommunication
        TelecommunicationDto authorTelecommunication = TelecommunicationDto.builder()
                .value("(555)555-1002")
                .use("Work Place")
                .build();

        //Mock patient Author
        AuthorDto authorDto = AuthorDto.builder()
                .providerName("Nancy Nightingale")
                .organizationName("Get Well Clinic")
                .softwareUse("Software used")
                .nationalProviderId(null)
                .contactInfo(ContactInfoDto.builder()
                        .address(authorAddress)
                        .telecommunications(Arrays.asList(authorTelecommunication))
                        .build())
                .build();

        //Mock patient Section1
        SectionDto sectionDto1 = SectionDto.builder()
                .title("ENCOUNTERS")
                .content("<div><table><thead><tr><th>Encounter</th><th>Performer</th><th>Location</th><th>Date</th></tr></thead><tbody><tr><td><span></span> Pnuemonia</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20120806</td></tr><tr><td><span></span>Asthma</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20140116</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section2
        SectionDto sectionDto2 = SectionDto.builder()
                .title("HOSPITAL DISCHARGE MEDICATIONS")
                .content("<div><table><thead><tr><th>Medication</th><th>Directions</th><th>Start Date</th><th>Status</th><th>Indications</th><th>Fill Instructions</th></tr></thead><tbody><tr><td><span>120 ACTUAT Fluticasone propionate 0.11 MG/ACTUAT Metered Dose Inhaler</span></td><td><span>0.11 MG/ACTUAT Metered Dose Once Daily</span></td><td>20120813</td><td>Active</td><td>Bronchitis (32398004 SNOMED CT)</td><td><span>Generic Substitition Allowed</span></td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section3
        SectionDto sectionDto3 = SectionDto.builder()
                .title("ALLERGIES, ADVERSE REACTIONS, ALERTS")
                .content("<div><table><thead><tr><th>Substance</th><th>Reaction</th><th>Severity</th><th>Status</th></tr></thead><tbody><tr><td>Penicillin G benzathine</td><td><span>Hives</span></td><td><span>Moderate to severe</span></td><td>Inactive</td></tr><tr><td>Codeine</td><td><span>Shortness of Breath</span></td><td><span>Moderate</span></td><td>Active</td></tr><tr><td>Aspirin</td><td><span>Hives</span></td><td><span>Mild to moderate</span></td><td>Active</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient cdAdocument
        CdaDocumentDto cdaDocumentDto = CdaDocumentDto.builder()
                .date(LocalDate.of(2012, 9, 12))
                .type("Summarization of Episode Note")
                .id("Test CCDA^1.1.1.1.1.1.1.1.1")
                .targetPatient(targetPatient)
                .treatment(treatmentDto)
                .authors(Arrays.asList(authorDto))
                .title("Health Summary")
                .sections(Arrays.asList(sectionDto1, sectionDto2, sectionDto3))
                .build();

        //Mock patient document
        DocumentDto documentDto = DocumentDto.builder()
                .cdaDocuments(Arrays.asList(cdaDocumentDto))
                .build();

        return PatientHealthDataDto.builder()
                .documents(Arrays.asList(documentDto))
                .build();
    }
}
