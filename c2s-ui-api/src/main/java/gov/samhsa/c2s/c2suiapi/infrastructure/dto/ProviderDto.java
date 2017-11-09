package gov.samhsa.c2s.c2suiapi.infrastructure.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProviderDto {
    @NotNull
    private Long id;
}
