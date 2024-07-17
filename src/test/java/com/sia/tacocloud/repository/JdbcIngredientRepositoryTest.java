package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.model.Ingredient.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JdbcIngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void ingredientRepositorySaveTest() {
        Ingredient ingredient = new Ingredient("TEST", "Test ingredient", Type.WRAP, true);
        ingredientRepository.save(ingredient);
        Ingredient result = ingredientRepository.findById("TEST").orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("TEST", result.getId());
        Assertions.assertEquals(Type.WRAP, result.getType());
    }

    @Test
    public void ingredientRepositoryFindAllTest() {
        var result = ingredientRepository.findAll();

        Assertions.assertNotNull(result);
        List<Ingredient> listResult = new ArrayList<>();
        result.forEach(listResult::add);
        Assertions.assertEquals(10, listResult.size());
    }
}
