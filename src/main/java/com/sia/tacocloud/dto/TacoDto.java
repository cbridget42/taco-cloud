package com.sia.tacocloud.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDto {

    private String name;
    private List<IngredientDto> ingredients;
}
