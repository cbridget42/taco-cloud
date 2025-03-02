package com.sia.taco.cloud.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDto {

    private String name;
    private List<IngredientDto> ingredients;
}
