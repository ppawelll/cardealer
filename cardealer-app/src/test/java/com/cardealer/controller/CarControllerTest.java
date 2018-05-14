package com.cardealer.controller;


import com.cardealer.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CarControllerTest {

    private MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
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



