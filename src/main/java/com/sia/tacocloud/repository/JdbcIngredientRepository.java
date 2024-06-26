package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    public static String SELECT_ALL_INGREDIENTS = "SELECT id, name, type FROM ingredient";
    public static String SELECT_INGREDIENT_BY_ID = "SELECT id, name, type FROM ingredient WHERE id=?";
    public static String INSERT_INGREDIENT = "INSERT INTO ingredient (id, name, type) VALUES (?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
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
