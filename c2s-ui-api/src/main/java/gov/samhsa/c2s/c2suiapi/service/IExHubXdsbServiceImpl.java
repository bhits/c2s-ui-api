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
        ProviderDto providerDto3 = ProviderDto.builder()
                .providerName("Dr. Tyler Dental")
                .organizationName("Community Health Clinic")
                .softwareUse("Software used")
                .nationalProviderId(null)
                .contactInfo(null)
                .build();
        ProviderDto providerDto4 = ProviderDto.builder()
                .providerName("Dr. Potomac Dental")
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

        TreatmentDto treatmentDto2 = TreatmentDto.builder()
                .service("Opioid Abuse Disorder\\nTreatment")
                .serviceStartDate(LocalDate.of(2014, 6, 6))
                .serviceEndDate(LocalDate.of(2014, 7, 13))
                .providers(Arrays.asList(providerDto3, providerDto4))
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

        AuthorDto authorDto2 = AuthorDto.builder()
                .providerName("Tyler Dental")
                .organizationName("Get Well Clinic")
                .softwareUse("Software used")
                .nationalProviderId(null)
                .contactInfo(ContactInfoDto.builder()
                        .address(authorAddress)
                        .telecommunications(Arrays.asList(authorTelecommunication))
                        .build())
                .build();

        //Mock patient Section0
        SectionDto sectionDto0 = SectionDto.builder()
                .title("MEDICATIONS")
                .content("<div><table><thead><tr><th>Date</th><th>Prescription</th><th>Directions</th><th>Status</th><th>Indications</th><th>Fill Instructions</th></tr></thead><tbody><tr><td>2/12/2016</td><td><span>Methadone 1mg/ml SF</span></td><td>3 doses, 40 ml</td><td>Active</td><td>Opioid Abuse Disorder</td><td><span>Packaged doses</span></td></tr><tr><td>2/12/2016</td><td>Albuterol 0.09 MG/ACTUAT inhalant solution</td><td>0.09 MG/ACTUAT inhalant solution, 2 puffs once</td><td>Active</td><td>Asthma</td><td>Generic Substitition Allowed</td></tr><tr><td>2/15/2016</td><td>Methadone 1mg/ml SF</td><td>2 doses, 40 ml</td><td>Active</td><td>Opioid Abuse Disorder</td><td>Packaged doses</td></tr><tr><td>2/17/2016</td><td>Methadone 1mg/ml SF</td><td>3 doses, 40 ml</td><td>Active</td><td>Opioid Abuse Disorder</td><td>Packaged doses</td></tr><tr><td>2/29/2016</td><td>Methadone 1mg/ml SF</td><td>3 doses, 40 ml</td><td>Active</td><td>Opioid Abuse Disorder</td><td>Packaged doses</td></tr><tr><td>3/03/2016</td><td>Methadone 1mg/ml SF</td><td>3 doses, 40 ml</td><td>Active</td><td>Opioid Abuse Disorder</td><td>Packaged doses</td></tr><tr><td>3/03/2016</td><td>Bupropion Hydrochloride 100 MG [Wellbutrin] Tablets</td><td>30 doses, 100 mg tablets</td><td>Active</td><td>Depressive Disorder</td><td>Generic Substitition Allowed</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section1
        SectionDto sectionDto1 = SectionDto.builder()
                .title("HOSPITAL DISCHARGE MEDICATIONS")
                .content("<div><table><thead><tr><th>Medication</th><th>Directions</th><th>Start Date</th><th>Status</th><th>Indications</th><th>Fill Instructions</th></tr></thead><tbody><tr><td><span>120 ACTUAT Fluticasone propionate 0.11 MG/ACTUAT Metered Dose Inhaler</span></td><td><span>0.11 MG/ACTUAT Metered Dose Once Daily</span></td><td>20120813</td><td>Active</td><td>Bronchitis (32398004 SNOMED CT)</td><td><span>Generic Substitition Allowed</span></td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section2
        SectionDto sectionDto2 = SectionDto.builder()
                .title("ALLERGIES, ADVERSE REACTIONS, ALERTS")
                .content("<div><table><thead><tr><th>Substance</th><th>Reaction</th><th>Severity</th><th>Status</th></tr></thead><tbody><tr><td>Penicillin G benzathine</td><td><span>Hives</span></td><td><span>Moderate to severe</span></td><td>Inactive</td></tr><tr><td>Codeine</td><td><span>Shortness of Breath</span></td><td><span>Moderate</span></td><td>Active</td></tr><tr><td>Aspirin</td><td><span>Hives</span></td><td><span>Mild to moderate</span></td><td>Active</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section3
        SectionDto sectionDto3 = SectionDto.builder()
                .title("ENCOUNTERS")
                .content("<div><table><thead><tr><th>Encounter</th><th>Performer</th><th>Location</th><th>Date</th></tr></thead><tbody><tr><td><span></span> Pnuemonia</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20120806</td></tr><tr><td><span></span>Asthma</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20140116</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section4
        SectionDto sectionDto4 = SectionDto.builder()
                .title("IMMUNIZATIONS")
                .content("<div><span></span><table><thead><tr><th>Vaccine</th><th>Date</th><th>Status</th></tr></thead><tbody><tr><td><span></span>Influenza virus vaccine, IM</td><td>May 2012</td><td>Completed</td></tr><tr><td><span></span>Tetanus and diphtheria toxoids, IM</td><td>Apr 2012</td><td>Completed</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section5
        SectionDto sectionDto5 = SectionDto.builder()
                .title("CARE PLAN")
                .content("<div><table><thead><tr><th>Planned Activity</th><th>Planned Date</th></tr></thead><tbody><tr><td>Consultation with Dr George Potomac for Asthma</td><td>20120820</td></tr><tr><td>Chest X-ray</td><td>20120826</td></tr><tr><td>Sputum Culture</td><td>20120820</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section6
        SectionDto sectionDto6 = SectionDto.builder()
                .title("REASON FOR REFERRAL")
                .content("<div><p>Follow up with Dr George Potomac for Asthma</p></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section7
        SectionDto sectionDto7 = SectionDto.builder()
                .title("PROBLEMS")
                .content("<div><span></span><ol><li>Opioid Abuse Disorder : Status - Active</li><li>Asthma : Status - Active</li><li>Depressive Disorder : Status - Active</li></ol></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section8
        SectionDto sectionDto8 = SectionDto.builder()
                .title("PROCEDURES")
                .content("<div><table><thead><tr><th>Procedure</th><th>Date</th></tr></thead><tbody><tr><td><span>Chest X-Ray</span></td><td>8/7/2012</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section9
        SectionDto sectionDto9 = SectionDto.builder()
                .title("FUNCTIONAL STATUS")
                .content("<div><table><thead><tr><th>Functional Condition</th><th>Effective Dates</th><th>Condition Status</th></tr></thead><tbody><tr><td><span>Dependence on cane</span></td><td>2008</td><td>Active</td></tr><tr><td><span>Memory impairment</span></td><td>2008</td><td>Active</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section10
        SectionDto sectionDto10 = SectionDto.builder()
                .title("RESULTS")
                .content("<div><table><tbody><tr><td>LABORATORY INFORMATION</td></tr><tr><td>Chemistries and drug levels</td></tr><tr><td><span>HGB (M 13-18 g/dl; F 12-16 g/dl)</span></td><td>13.2</td></tr><tr><td><span>WBC (4.3-10.8 10+3/ul)</span></td><td>6.7</td></tr><tr><td><span>PLT (135-145 meq/l)</span></td><td>123 (L)</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section11
        SectionDto sectionDto11 = SectionDto.builder()
                .title("SOCIAL HISTORY")
                .content("<div><table><thead><tr><th>Social History Element</th><th>Description</th><th>Effective Dates</th></tr></thead><tbody><tr><td><span></span> smoking</td><td>Former Smoker (1 pack per day</td><td>20050501 to 20110227</td></tr><tr><td><span></span> smoking</td><td>Current Everyday Smoker 2 packs per day</td><td>20110227 - today</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section12
        SectionDto sectionDto12 = SectionDto.builder()
                .title("VITAL SIGNS")
                .content("<div><table><thead><tr><th>Date / Time: </th><th>Nov 1, 2011</th><th>August 6, 2012</th></tr></thead><tbody><tr><th>Height</th><td><span>69 inches</span></td><td><span>69 inches</span></td></tr><tr><th>Weight</th><td><span>189 lbs</span></td><td><span>194 lbs</span></td></tr><tr><th>Blood Pressure</th><td><span>132/86 mmHg</span></td><td><span>145/88 mmHg</span></td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section13
        SectionDto sectionDto13 = SectionDto.builder()
                .title("HOSPITAL DISCHARGE INSTRUCTIONS")
                .content("<div><span>Ms. Share, you have been seen by Dr. Nightingale at Local Community Hospital from August 8th until August 13th 2014. You are currently being discharged from Local Community Hospital. Dr. Nightingale has provided the following instructions to you at this time; should you have any questions please contact a member of your healthcare team prior to discharge. If you have left the hospital and have questions, please contact Dr. Nightingale at 555-555-1002. Instructions:</span><ol><li>Take all medications as prescribed.</li><li>Please monitor your peak flows. If your peak flows drop to 50% of normal, call my office immediately or return to the Emergency Room.</li><li>If you experience any of the following symptoms, call my office immediately or return to the Emergency Room:                        <ol><li>Shortness of Breath</li><li>Dizziness or Light-headedness</li><li>Fever, chills, or diffuse body aches</li><li>Pain or redness at the site of any previous intravenous catheter</li><li>Any other unusual problem</li></ol></li></ol></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section14
        SectionDto sectionDto14 = SectionDto.builder()
                .title("CARE PLAN")
                .content("<div><table><thead><tr><th>Planned Activity</th><th>Planned Date</th></tr></thead><tbody><tr><td>Consultation with Dr George Potomac for Asthma</td><td>20120820</td></tr><tr><td>Chest X-ray</td><td>20120826</td></tr><tr><td>Sputum Culture</td><td>20120820</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section15
        SectionDto sectionDto15 = SectionDto.builder()
                .title("ENCOUNTERS")
                .content("<div><table><thead><tr><th>Encounter</th><th>Performer</th><th>Location</th><th>Date</th></tr></thead><tbody><tr><td><span></span> Pnuemonia</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20120806</td></tr><tr><td><span></span>Asthma</td><td>Dr Nancy Nightingale</td><td>Community Health Clinic</td><td>20140116</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient Section16
        SectionDto sectionDto16 = SectionDto.builder()
                .title("PROCEDURES")
                .content("<div><table><thead><tr><th>Procedure</th><th>Date</th></tr></thead><tbody><tr><td><span>Chest X-Ray</span></td><td>8/7/2012</td></tr></tbody></table></div>")
                .contentMimeType("text/html")
                .author(null)
                .m_ClinicalStatements(null)
                .beid(null)
                .build();

        //Mock patient cdADocuments
        CdaDocumentDto cdaDocumentDto1 = CdaDocumentDto.builder()
                .date(LocalDate.of(2012, 9, 12))
                .type("Summarization of Episode Note")
                .id("Test CCDA^1.1.1.1.1.1.1.1.1")
                .targetPatient(targetPatient)
                .treatment(treatmentDto)
                .authors(Arrays.asList(authorDto))
                .title("Health Summary")
                .sections(Arrays.asList(
                        sectionDto0, sectionDto1, sectionDto2, sectionDto3,
                        sectionDto4, sectionDto5, sectionDto6, sectionDto7,
                        sectionDto8, sectionDto9, sectionDto10, sectionDto11,
                        sectionDto12, sectionDto13))
                .build();

        CdaDocumentDto cdaDocumentDto2 = CdaDocumentDto.builder()
                .date(LocalDate.of(2014, 2, 20))
                .type("Summarization of Episode Note")
                .id("Test CCDA^2.2.2.2.2.2.2.2.2")
                .targetPatient(targetPatient)
                .treatment(treatmentDto2)
                .authors(Arrays.asList(authorDto2))
                .title("Health Summary")
                .sections(Arrays.asList(
                        sectionDto14, sectionDto15, sectionDto16))
                .build();

        //Mock patient document
        DocumentDto documentDto = DocumentDto.builder()
                .cdaDocuments(Arrays.asList(cdaDocumentDto1, cdaDocumentDto2))
                .build();

        return PatientHealthDataDto.builder()
                .documents(Arrays.asList(documentDto))
                .build();
    }
}
