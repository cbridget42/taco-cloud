package com.sia.tacocloud.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "taco_order", schema = "taco_cloud")
@NoArgsConstructor
@AllArgsConstructor
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final Date placedAt = new Date();
    @NotBlank
    private String deliveryName;
    @NotBlank
    private String deliveryStreet;
    @NotBlank
    private String deliveryCity;
    @NotBlank
    @Size(max = 2, message = "State must not be more than 2 characters long")
    private String deliveryState;
    @NotBlank
    @Size(max = 10, message = "Zip must not be more than 10 characters long")
    private String deliveryZip;
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCvv;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @OneToMany(mappedBy = "tacoOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Taco> tacos = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "taco_user")
    private TacoUser tacoUser;

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
