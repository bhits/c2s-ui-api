package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.AddressDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.AuthorDto;
import gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb.CDAdocumentDto;
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
        ProviderDto providerDto = ProviderDto.builder()
                .providerName("Dr. Nancy Nightingale")
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
                .providers(Arrays.asList(providerDto))
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
                .title("MEDICATIONS")
                .content("")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section2
        SectionDto sectionDto2 = SectionDto.builder()
                .title("HOSPITAL DISCHARGE MEDICATIONS")
                .content("")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section3
        SectionDto sectionDto3 = SectionDto.builder()
                .title("ALLERGIES, ADVERSE REACTIONS, ALERTS")
                .content("")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient cdAdocument
        CDAdocumentDto cdAdocumentDto = CDAdocumentDto.builder()
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
                .CDAdocuments(Arrays.asList(cdAdocumentDto))
                .build();

        return PatientHealthDataDto.builder()
                .Documents(Arrays.asList(documentDto))
                .build();
    }
}
