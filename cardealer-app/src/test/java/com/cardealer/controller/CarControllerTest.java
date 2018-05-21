package com.cardealer.controller;


import com.cardealer.dto.CarDTO;
import com.cardealer.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        CarDTO carDTO = new CarDTO("opel", "vectra", 2003, 10000.0);

        mockMvc.perform(post("/car/insert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(carDtoToJson(carDTO))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated());
    }

    @Test
    public void testFailInsertCar() throws Exception {
        CarDTO carDTO = new CarDTO("", "vectra", 2003, 15000.0);


        mockMvc.perform(post("/car/insert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(carDtoToJson(carDTO))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .is(500));
    }

    private String carDtoToJson(CarDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(dto);

    }
}



