package com.sia.taco.cloud.server.mapper;

import com.sia.taco.cloud.api.dto.IngredientDto;
import com.sia.taco.cloud.api.entity.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientsMapper {

    Ingredient mapToIngredient(IngredientDto source);
}
