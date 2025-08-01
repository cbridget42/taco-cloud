package com.sia.taco.cloud.server.controller;

import com.sia.taco.cloud.api.dto.IngredientDto;
import com.sia.taco.cloud.api.entity.Ingredient;
import com.sia.taco.cloud.server.mapper.IngredientsMapper;
import com.sia.taco.cloud.server.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientRepository ingredientRepository;
    private final IngredientsMapper ingredientsMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> ingredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/save", consumes = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ingredient> saveTaco(@RequestBody IngredientDto ingredient) {
        return ResponseEntity.of(
                Optional.of(ingredientRepository.save(ingredientsMapper.mapToIngredient(ingredient)))
        );
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable("id") String id) {
        ingredientRepository.deleteById(id);
    }
}
