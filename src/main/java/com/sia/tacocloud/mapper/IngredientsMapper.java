package com.sia.tacocloud.mapper;

import com.sia.tacocloud.dto.IngredientDto;
import com.sia.tacocloud.entity.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientsMapper {

    Ingredient mapToIngredient(IngredientDto source);
}
