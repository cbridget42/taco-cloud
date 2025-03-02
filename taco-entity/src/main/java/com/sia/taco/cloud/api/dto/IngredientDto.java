package com.sia.taco.cloud.api.dto;

import com.sia.taco.cloud.api.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientDto {

    private String id;
    private String name;
    private Ingredient.Type type;
}
