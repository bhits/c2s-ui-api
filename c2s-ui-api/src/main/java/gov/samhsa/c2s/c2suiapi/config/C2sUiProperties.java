package gov.samhsa.c2s.c2suiapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "c2s.c2s-ui")
@Data
public class C2sUiProperties {

    @NotNull
    @Valid
    private Features features;

    @Data
    public static class Features {
        private boolean healthInformationEnabled;
        private boolean demoDisclaimerEnabled;
    }


}
