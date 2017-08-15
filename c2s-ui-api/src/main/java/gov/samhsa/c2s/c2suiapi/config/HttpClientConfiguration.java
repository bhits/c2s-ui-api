package gov.samhsa.c2s.c2suiapi.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ApacheHttpClient.class)
@ConditionalOnProperty(value = "feign.httpclient.enabled", matchIfMissing = true)
public class HttpClientConfiguration {

    @Autowired(required = false)
 private HttpClient httpClient;

        @ConditionalOnMissingBean
 		public Client feignClient() {
            if (httpClient != null) {
                    return new ApacheHttpClient(httpClient);
                }
            return new ApacheHttpClient();
        }
}
