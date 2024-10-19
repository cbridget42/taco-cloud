package com.sia.tacocloud.config;

import com.sia.tacocloud.entity.Ingredient;
import com.sia.tacocloud.entity.Ingredient.Type;
import com.sia.tacocloud.repository.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.emptyList;

@Configuration
public class AppConfig {

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP, emptyList()));
            repository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP, emptyList()));
            repository.save(new Ingredient("GRBF", "Ground beef", Type.PROTEIN, emptyList()));
            repository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN, emptyList()));
            repository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES, emptyList()));
            repository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES, emptyList()));
            repository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE, emptyList()));
            repository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE, emptyList()));
            repository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE, emptyList()));
            repository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE, emptyList()));
        };
    }
}
