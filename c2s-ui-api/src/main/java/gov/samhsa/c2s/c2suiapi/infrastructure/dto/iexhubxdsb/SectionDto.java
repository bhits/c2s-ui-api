package gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Todo: Remove it when integrate with iexhub-xdsb
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDto {
    private String title;
    private String content;
    private String contentMimeType;
    private AuthorDto author;
    private String m_ClinicalStatements;
    private String beid;
}
