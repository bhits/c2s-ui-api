package gov.samhsa.c2s.c2suiapi.infrastructure.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Data
@ScriptAssert(alias = "_", lang = "javascript", script = "_.hasSystemOrOid()")
public class CareTeamMemberIdentifierDto {
    @NotBlank
    private String value;

    private String system;
    private String oid;
    private String display;

    @NotNull
    private int priority;

    public boolean hasSystemOrOid() {
        return StringUtils.hasText(system) || StringUtils.hasText(oid);
    }
}
