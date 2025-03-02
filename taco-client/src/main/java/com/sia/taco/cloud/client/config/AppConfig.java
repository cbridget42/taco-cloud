package com.sia.taco.cloud.client.config;

import com.sia.taco.cloud.api.dto.IngredientDto;
import com.sia.taco.cloud.api.entity.Ingredient;
import com.sia.taco.cloud.client.client.RestTacoClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.TlsSocketStrategy;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(SSLContext context) {

        var socketFactoryRegistry = RegistryBuilder.<TlsSocketStrategy>create()
                .register("https", new DefaultClientTlsStrategy(context, (hostname, session) -> true))
                .build();
        var httpClient = HttpClients.custom()
                .setConnectionManager(BasicHttpClientConnectionManager.create(socketFactoryRegistry))
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Bean
    public ApplicationRunner appRunner(RestTacoClient client) {
        return args -> {
            client.getIngredientById("CARN");
            client.createIngredient(new IngredientDto("CHTS", "CHITOS", Ingredient.Type.CHEESE));
            client.getIngredientById("CHTS");
        };
    }

    @Bean
    public SSLContext sslContexts() throws Exception {
        return new SSLContextBuilder()
                .loadTrustMaterial(
                        new ClassPathResource("keys/mykeys.jks").getURL(),
                        "qwerty".toCharArray()
                )
                .build();
    }
}
