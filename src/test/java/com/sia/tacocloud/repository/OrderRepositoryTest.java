package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.IngredientRef;
import com.sia.tacocloud.model.Taco;
import com.sia.tacocloud.model.TacoOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {

    public static final String KEK = "kek";

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderRepositorySaveTest() {
        Taco taco = new Taco();
        taco.setCreatedAt(new Date());
        taco.setName(KEK);
        IngredientRef ingredientRef = new IngredientRef("FLTO");
        taco.setIngredients(List.of(ingredientRef));

        TacoOrder tacoOrder = createTacoOrder(taco);
        Long id = orderRepository.save(tacoOrder).getId();

        TacoOrder result = orderRepository.findById(id).orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(KEK, result.getDeliveryName());
        Taco resultTaco = result.getTacos()
                .stream()
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(resultTaco);
        Assertions.assertEquals(KEK, resultTaco.getName());
    }

    private TacoOrder createTacoOrder(Taco taco) {
        TacoOrder tacoOrder = new TacoOrder();

        tacoOrder.setPlacedAt(new Date());
        tacoOrder.setCcCVV(KEK);
        tacoOrder.setCcExpiration(KEK);
        tacoOrder.setCcNumber(KEK);
        tacoOrder.setDeliveryName(KEK);
        tacoOrder.setDeliveryCity(KEK);
        tacoOrder.setDeliveryState("lo");
        tacoOrder.setDeliveryStreet(KEK);
        tacoOrder.setDeliveryZip(KEK);
        tacoOrder.addTaco(taco);

        return tacoOrder;
    }
}
