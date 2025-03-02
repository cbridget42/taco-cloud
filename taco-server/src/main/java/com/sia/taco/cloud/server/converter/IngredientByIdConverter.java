package com.sia.taco.cloud.server.converter;

import com.sia.taco.cloud.server.repository.IngredientRepository;
import com.sia.taco.cloud.api.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id)
                .orElse(null);
    }
}
