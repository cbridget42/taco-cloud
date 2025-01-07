package com.sia.tacocloud.controller;

import com.sia.tacocloud.config.properties.OrderProperties;
import com.sia.tacocloud.repository.IngredientRepository;
import com.sia.tacocloud.repository.OrderRepository;
import com.sia.tacocloud.repository.UserRepository;
import com.sia.tacocloud.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class HomeTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IngredientRepository mockIngredientRepo;
    @MockitoBean
    private OrderRepository orderRepository;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private AdminService adminService;
    @MockitoBean
    private OrderProperties orderProperties;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")));
    }
}
