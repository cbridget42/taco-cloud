package com.sia.tacocloud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(schema = "taco_cloud")
@NoArgsConstructor
@AllArgsConstructor
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final Date createdAt = new Date();
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;
    private Long tacoOrderKey;

    @ManyToOne
    @JoinColumn(name = "taco_order")
    private TacoOrder tacoOrder;

    @ManyToMany
    @JoinTable(
            name = "ingredient_ref",
            schema = "taco_cloud",
            joinColumns = @JoinColumn(name = "taco"),
            inverseJoinColumns = @JoinColumn(name = "ingredient")
    )
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    @Override
    public String toString() {
        var tacoOrderString = Optional.ofNullable(tacoOrder)
                .map(TacoOrder::getId)
                .orElse(null);
        return "Taco{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", tacoOrderKey=" + tacoOrderKey +
                ", tacoOrder=" + tacoOrderString +
                ", ingredients=" + ingredients +
                '}';
    }
}
