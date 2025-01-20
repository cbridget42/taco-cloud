package com.sia.tacocloud.dto;

import com.sia.tacocloud.entity.Ingredient;
import lombok.Data;

@Data
public class IngredientDto {

    private String id;
    private String name;
    private Ingredient.Type type;
}
