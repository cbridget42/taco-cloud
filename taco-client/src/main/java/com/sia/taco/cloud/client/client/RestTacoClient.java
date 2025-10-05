package com.sia.taco.cloud.client.client;

import com.sia.taco.cloud.api.dto.IngredientDto;
import com.sia.taco.cloud.client.parser.CommonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTacoClient {

    private final RestTemplate restTemplate;
    private final CommonParser commonParser;

    public IngredientDto getIngredientById(String ingredientId) {
        ResponseEntity<IngredientDto> responseEntity =
                restTemplate.getForEntity("https://localhost:6969/api/ingredients/{id}",
                        IngredientDto.class, ingredientId);

        var instant = Instant.ofEpochMilli(responseEntity.getHeaders().getDate());
        log.info("Fetched time: {}", instant.atZone(ZoneId.systemDefault()).toOffsetDateTime());
        log.info("Ingredient: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public IngredientDto createIngredient(IngredientDto ingredient) {
        var responseEntity = restTemplate.postForEntity("https://localhost:6969/api/ingredients/save",
                ingredient,
                String.class);

        var bodyRs = responseEntity.getBody();
        log.info("response received from /api/ingredients/save with body: {}", bodyRs);
        var response = commonParser.parse(IngredientDto.class, bodyRs);
        if (nonNull(response)) {
            log.info("New ingredient created {}", response);
        }
        return response;
    }

    public void deleteIngredient(String ingredientId) {
        restTemplate.delete("https://localhost:6969/api/ingredients/delete/" + ingredientId);
    }
}
