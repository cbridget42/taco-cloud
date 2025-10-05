package com.sia.taco.cloud.client.config;

import com.sia.taco.cloud.client.interceptor.BearerTokenInterceptor;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(BearerTokenInterceptor interceptor) throws Exception {
        // Создаем контекст, который доверяет всем сертификатам
        var sslContext = SSLContexts.custom()
                .loadTrustMaterial((chain, authType) -> true)
                .build();

        var tlsSocketStrategy = new DefaultClientTlsStrategy(sslContext, NoopHostnameVerifier.INSTANCE);

        var connManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setTlsSocketStrategy(tlsSocketStrategy)
                .build();

        var client = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();

        var restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        restTemplate.getInterceptors().add(interceptor);

        return restTemplate;
    }
}
