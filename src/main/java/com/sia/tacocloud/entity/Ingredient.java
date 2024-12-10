package com.sia.tacocloud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(schema = "taco_cloud")
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany(mappedBy = "ingredients")
    private List<Taco> tacos;

    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
