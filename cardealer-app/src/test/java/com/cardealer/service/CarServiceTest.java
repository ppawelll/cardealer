package com.cardealer.service;

import com.cardealer.dto.CarDTO;
import com.cardealer.model.CarModel;
import com.cardealer.repository.CarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService = new CarService();

    @Mock
    private CarRepository carRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCar() {
        CarDTO carDTO = new CarDTO("nissan", "gto", 1999, 120000.00);
        carService.saveCar(carDTO);

        verify(carRepository, times(1)).save(any(CarModel.class));
    }

    @Test
    public void testSaveCarException() {
        CarDTO carDTO = new CarDTO("nissan", "gto", 1999, 120000.00);
        when(carRepository.save(any(CarModel.class))).thenThrow(new RuntimeException());

        try {
            carService.saveCar(carDTO);
            Assert.assertTrue(false);
        } catch (Exception e) {
            // check that exception was thrown
            Assert.assertTrue(true);
        }
    }
}
