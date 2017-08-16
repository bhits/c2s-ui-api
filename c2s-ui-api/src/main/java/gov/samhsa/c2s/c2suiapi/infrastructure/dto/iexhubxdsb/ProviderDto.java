package gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderDto {
    private String providerName;
    private String organizationName;
    private String softwareUse;
    private String nationalProviderId;
    private ContactInfoDto contactInfo;
}
