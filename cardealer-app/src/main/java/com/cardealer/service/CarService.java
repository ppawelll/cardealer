package com.cardealer.service;

import com.cardealer.dto.CarDTO;
import com.cardealer.model.CarModel;
import com.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void saveCar(CarDTO carDTO) {

        CarModel carModel = new CarModel(carDTO.getMake(), carDTO.getModel(), carDTO.getYear(), carDTO.getPrice());
        carRepository.save(carModel);
    }
}
