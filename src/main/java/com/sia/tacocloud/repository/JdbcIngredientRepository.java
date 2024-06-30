package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {

    public static final String SELECT_ALL_INGREDIENTS = "SELECT id, name, type FROM ingredient";
    public static final String SELECT_INGREDIENT_BY_ID = "SELECT id, name, type FROM ingredient WHERE id=?";
    public static final String INSERT_INGREDIENT = "INSERT INTO ingredient (id, name, type) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query(SELECT_ALL_INGREDIENTS, this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var results = jdbcTemplate.query(SELECT_INGREDIENT_BY_ID, this::mapRowToIngredient, id);
        return results
                .stream()
                .findFirst();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                INSERT_INGREDIENT,
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }
}
