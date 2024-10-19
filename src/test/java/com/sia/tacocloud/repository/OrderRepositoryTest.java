package com.sia.tacocloud.repository;

import com.sia.tacocloud.entity.Ingredient;
import com.sia.tacocloud.entity.Taco;
import com.sia.tacocloud.entity.TacoOrder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {

    public static final String KEK = "kek";
    public static final String CC_CVV = "333";
    public static final String CC_EXPIRATION = "11/22";
    public static final String CC_NUMBER = "5555555555554444";
    public static final String DELIVERY_STATE = "DS";
    public static final String TACO_NAME = "tacoName";

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void orderRepositorySaveTest() {
        Taco taco = new Taco();
        taco.setName(TACO_NAME);
        Ingredient ingredient = new Ingredient();
        ingredient.setId("FLTO");
        taco.setIngredients(List.of(ingredient));

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
        Assertions.assertEquals(TACO_NAME, resultTaco.getName());
    }

    private TacoOrder createTacoOrder(Taco taco) {
        TacoOrder tacoOrder = new TacoOrder();

        tacoOrder.setCcCvv(CC_CVV);
        tacoOrder.setCcExpiration(CC_EXPIRATION);
        tacoOrder.setCcNumber(CC_NUMBER);
        tacoOrder.setDeliveryName(KEK);
        tacoOrder.setDeliveryCity(KEK);
        tacoOrder.setDeliveryState(DELIVERY_STATE);
        tacoOrder.setDeliveryStreet(KEK);
        tacoOrder.setDeliveryZip(KEK);
        tacoOrder.addTaco(taco);

        return tacoOrder;
    }
}
