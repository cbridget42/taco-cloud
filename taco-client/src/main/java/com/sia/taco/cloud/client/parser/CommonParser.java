package com.sia.taco.cloud.client.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonParser {

    private final ObjectMapper objectMapper;

    public <T> T parse(Class<T> clazz, String body) {
        T result = null;
        try {
            result = objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException jsonException) {
            log.error("Failed to deserialize response JSON: {}", jsonException.getMessage());
        }

        return result;
    }
}
