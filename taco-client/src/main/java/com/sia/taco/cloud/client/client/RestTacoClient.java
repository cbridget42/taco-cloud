package com.sia.taco.cloud.client.client;

import com.sia.taco.cloud.api.dto.IngredientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTacoClient {

    private final RestTemplate restTemplate;

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
                IngredientDto.class);//todo залогировать сырой json

        log.info("New ingredient created {}", responseEntity.getBody());
        return responseEntity.getBody();
    }
}
