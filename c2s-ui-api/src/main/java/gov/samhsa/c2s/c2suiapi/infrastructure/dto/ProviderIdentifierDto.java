package gov.samhsa.c2s.c2suiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderIdentifierDto {
    String value;

    String system;

    String oid;

    String display;

    String priority;

    String description;
}
