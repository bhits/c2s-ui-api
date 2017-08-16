package gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {
    @JsonProperty("CDAdocuments")
    private List<CdaDocumentDto> cdaDocuments;
}
