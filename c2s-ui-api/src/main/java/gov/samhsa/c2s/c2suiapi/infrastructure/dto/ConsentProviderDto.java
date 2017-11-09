package gov.samhsa.c2s.c2suiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class ConsentProviderDto {
    protected Long id;
    @Valid
    @NotEmpty
    protected Set<CareTeamMemberIdentifierDto> identifiers = new HashSet<>();
    @Valid
    protected AddressDto address;
    protected Boolean deletable;
    private ProviderType providerType;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private String phoneNumber;

    enum ProviderType {
        PRACTITIONER, ORGANIZATION
    }
}