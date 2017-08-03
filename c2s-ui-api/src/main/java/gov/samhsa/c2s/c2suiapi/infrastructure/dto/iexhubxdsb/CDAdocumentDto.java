package gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

//Todo: Remove it when integrate with iexhub-xdsb
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CDAdocumentDto {
    private LocalDate date;
    private String type;
    private String id;
    private String title;
    private TargetPatientDto targetPatient;
    private TreatmentDto treatment;
    private List<AuthorDto> authors;
    private List<SectionDto> sections;
}
