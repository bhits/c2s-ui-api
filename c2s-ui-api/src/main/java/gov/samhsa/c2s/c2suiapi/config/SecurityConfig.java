package gov.samhsa.c2s.c2suiapi.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static gov.samhsa.c2s.common.oauth2.OAuth2ScopeUtils.hasScopes;

@Configuration
public class SecurityConfig {

    private static final String RESOURCE_ID = "c2sUiApi";

    @Bean
    public ResourceServerConfigurer resourceServer(SecurityProperties securityProperties) {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                if (securityProperties.isRequireSsl()) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/pcm/**").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.POST, "/pcm/**").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.DELETE, "/pcm/**").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.PUT, "/pcm/**").access(hasScopes("c2sUiApi.write"))

                        .antMatchers(HttpMethod.GET, "/ums/user/profile").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.GET, "/ums/user/fullProfile").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.PUT, "/ums/users/locale/**").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.POST, "/ums/users/verification/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ums/users/activation/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/ums/users/activation/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ums/users/*").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.PUT, "/ums/users/*").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.GET, "/ums/userCreationLookupInfo").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.PUT, "/ums/self-service/users/*").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.GET, "/ums/user-avatars/user/*/avatar").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.POST, "/ums/user-avatars/user/*/avatar").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.DELETE, "/ums/user-avatars/user/*/avatar").access(hasScopes("c2sUiApi.write"))

                        .antMatchers(HttpMethod.GET, "/vss/**").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.GET, "/pls/**").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.GET, "/try-policy/**").access(hasScopes("c2sUiApi.read"))

                        .antMatchers(HttpMethod.GET, "/phr/uploadedDocuments/**").access(hasScopes("c2sUiApi.read"))
                        .antMatchers(HttpMethod.POST, "/phr/uploadedDocuments/**").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.DELETE, "/phr/uploadedDocuments/**").access(hasScopes("c2sUiApi.write"))
                        .antMatchers(HttpMethod.PUT, "/phr/uploadedDocuments/**").access(hasScopes("c2sUiApi.write"))

                        .antMatchers(HttpMethod.GET, "/iexhub-xdsb/**").access(hasScopes("c2sUiApi.read"))

                        .antMatchers(HttpMethod.POST, "/uaa/login").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().denyAll();
            }
        };
    }
}