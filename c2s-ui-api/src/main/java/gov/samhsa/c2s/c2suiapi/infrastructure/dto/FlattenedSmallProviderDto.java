package gov.samhsa.c2s.c2suiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlattenedSmallProviderDto {
        private Long id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String genderCode;
        private String organizationName;
        private String enumerationDate;
        private String lastUpdateDate;
        private String entityTypeDisplayName;
        private String entityTypeCode;
        private String practiceLocationAddressFaxNumber;
        private String practiceLocationAddressTelephoneNumber;
        private String firstLinePracticeLocationAddress;
        private String secondLinePracticeLocationAddress;
        private String practiceLocationAddressCityName;
        private String practiceLocationAddressStateName;
        private String practiceLocationAddressPostalCode;
        private String practiceLocationAddressCountryCode;
        private List<ProviderIdentifierDto> identifiers = new ArrayList<>();
}
