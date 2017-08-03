package gov.samhsa.c2s.c2suiapi.infrastructure.dto.iexhubxdsb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Todo: Remove it when integrate with iexhub-xdsb
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfoDto {
    private AddressDto address;
    private List<TelecommunicationDto> telecommunications;
}
