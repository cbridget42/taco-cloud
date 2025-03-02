package com.sia.taco.cloud.server.repository;

import com.sia.taco.cloud.api.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {}
