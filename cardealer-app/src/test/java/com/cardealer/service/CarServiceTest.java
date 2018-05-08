package com.cardealer.service;

import com.cardealer.dto.CarDTO;
import com.cardealer.model.CarModel;
import com.cardealer.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarServiceTest {

    @Autowired
    CarService carService;

    @MockBean
    CarRepository carRepository;

    @Test
    public void testSaveCar() {
        CarDTO carDTO = new CarDTO("nissan", "gto", 1999, 120000.00);
        carService.saveCar(carDTO);

        verify(carRepository, times(1)).save(any(CarModel.class));
    }
}
