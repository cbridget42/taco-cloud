package com.sia.taco.cloud.server.service;

import com.sia.taco.cloud.api.entity.Ingredient;
import com.sia.taco.cloud.api.entity.Taco;
import com.sia.taco.cloud.api.entity.TacoOrder;
import com.sia.taco.cloud.server.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static com.sia.taco.cloud.server.repository.OrderRepositoryTest.TACO_NAME;
import static com.sia.taco.cloud.server.repository.OrderRepositoryTest.createTacoOrder;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@WithMockUser(roles = "ADMIN")
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void deleteAllOrdersTest() {
        Taco taco = new Taco();
        taco.setName(TACO_NAME);
        Ingredient ingredient = new Ingredient();
        ingredient.setId("FLTO");
        taco.setIngredients(List.of(ingredient));
        TacoOrder tacoOrder = createTacoOrder(taco);
        Long id = orderRepository.save(tacoOrder).getId();
        adminService.deleteAllOrders();
        assertNull(orderRepository.findById(id).orElse(null));
    }
}
