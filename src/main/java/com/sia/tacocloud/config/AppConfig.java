package com.sia.tacocloud.config;

import com.sia.tacocloud.model.Ingredient.Type;
import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.repository.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP, true));
            repository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP, true));
            repository.save(new Ingredient("GRBF", "Ground beef", Type.PROTEIN, true));
            repository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN, true));
            repository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES, true));
            repository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES, true));
            repository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE, true));
            repository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE, true));
            repository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE, true));
            repository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE, true));
        };
    }
}
