package gov.samhsa.c2s.c2suiapi.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "c2s.c2s-ui-api")
@Data
public class C2sUiApiProperties {
    @NotNull
    @Valid
    private Oauth2 oauth2;

    @Data
    public static class Oauth2 {
        @NotBlank
        private String baseUrl;

        @Valid
        private Client client;

        @Data
        public static class Client {
            @NotBlank
            private String clientId;

            @NotBlank
            private String clientSecret;
        }
    }
}
