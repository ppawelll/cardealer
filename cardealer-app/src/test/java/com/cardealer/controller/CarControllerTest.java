package com.cardealer.controller;


import com.cardealer.service.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest{

    private MockMvc mockMvc;

    @Autowired
    CarService carServiceMock;


    @Test
    @DisplayName("Test successful insert a car")
    public void testSuccessInsertCar() throws Exception {
        mockMvc.perform(post("car/insert")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(createCarInJson("opel", "capri", 1977, 233232.33))
                )
                .andExpect(MockMvcResultMatchers.status()
                .isCreated());

        System.out.println("Some testing should be here");
    }

    private static String createCarInJson(String make, String model, Integer year, Double price) {
        return "{ \"make\": \"" + make + "\", " +
                "\"model\":\"" + model + "\"," +
                "\"year\":\"" + year + "\"," +
                "\"price\":\"" + price + "\"}";
    }
}