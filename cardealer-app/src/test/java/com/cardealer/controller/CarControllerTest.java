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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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

    @Test
    public void testSuccessFindAll() throws Exception {
        CarDTO car1 = new CarDTO("mazda", "3", 2016, 65000.0);
        CarDTO car2 = new CarDTO("mazda", "6", 2016, 75000.0);

        when(carService.findAll()).thenReturn(Arrays.asList(car1,car2));

        ResultActions result = mockMvc.perform(get("/car/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        String content = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        assertEquals(ow.writeValueAsString(Arrays.asList(car1,car2)).replaceAll("\\s",""),
                content.replaceAll("\\s",""));

        verify(carService, times(1)).findAll();
        verifyNoMoreInteractions(carService);

    }

    @Test
    public void testSuccessUpdate() throws Exception {
        CarDTO car1 = new CarDTO("mazda", "3", 2016, 65000.0);
        mockMvc.perform(put("/car/update/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(carDtoToJson(car1))
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(carService, times(1)).update();
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void testFailUpdate() throws Exception {
        CarDTO car1 = new CarDTO("mazda", "3", 2016, 65000.0);

        doThrow(new EntityNotFoundException()).when(carService).update(anyLong(),any());

        mockMvc.perform(put("/car/update/{id}",11111111L)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(carDtoToJson(car1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(carService, times(1)).update();
        verifyNoMoreInteractions(carService);

    }

    private String carDtoToJson(CarDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(dto);

    }
}



