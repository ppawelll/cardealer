package com.cardealer.controller;


import com.cardealer.service.CarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest {

    private MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @DisplayName("Test: successful insert a car")
    public void testSuccessInsertCar() throws Exception {
        mockMvc.perform(post("/car/insert")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("make", "opel")
                .param("model", "vectra")
                .param("year", "2003")
                .param("price", "10000")
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated());
    }

    @Test
    @DisplayName("Test: fail insert a car")
    public void testFailInsertCar() throws Exception {


        mockMvc.perform(post("/car/insert")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("make", "") //make mustn’t be empty
                .param("model", "vectra")
                .param("year", "2003")
                .param("price", "15000")
        )
                .andExpect(MockMvcResultMatchers.status()
                        .is(500));
    }
}



