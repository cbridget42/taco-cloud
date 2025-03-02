package com.sia.taco.cloud.server.controller;

import com.sia.taco.cloud.api.entity.Ingredient;
import com.sia.taco.cloud.api.entity.Taco;
import com.sia.taco.cloud.api.entity.TacoOrder;
import com.sia.taco.cloud.server.repository.OrderRepository;
import com.sia.taco.cloud.server.repository.OrderRepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TacoControllerTest {

    public static final String TACO_SAVE_REQUEST = """
            {
                "name": "test1",
                "ingredients": [
                    {
                        "id": "CARN",
                        "name": "Carnitas",
                        "type": "PROTEIN"
                    }
                ]
            }
            """;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    public void setUp() {
        var ingredient = Ingredient.builder()
                .id("FLTO")
                .build();
        Taco taco = Taco.builder()
                .name(OrderRepositoryTest.TACO_NAME)
                .ingredients(List.of(ingredient))
                .build();

        TacoOrder tacoOrder = OrderRepositoryTest.createTacoOrder(taco);
        orderRepository.save(tacoOrder);
    }

    @Test
    public void recentTacoTest() throws Exception {
        mockMvc.perform(get("/api/tacos?recent"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(OrderRepositoryTest.TACO_NAME)))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void tacoByIdTestSuccess() throws Exception {
        mockMvc.perform(get("/api/tacos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(OrderRepositoryTest.TACO_NAME)))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void tacoByIdTestNotFound() throws Exception {
        mockMvc.perform(get("/api/tacos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveTaco() throws Exception {
        mockMvc.perform(post("/api/tacos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TACO_SAVE_REQUEST))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("tech order not found")));
    }
}
