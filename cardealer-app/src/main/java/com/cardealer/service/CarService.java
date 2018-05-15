package com.cardealer.service;

import com.cardealer.dto.CarDTO;
import com.cardealer.model.CarModel;
import com.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void saveCar(CarDTO carDTO) {

        CarModel carModel = new CarModel(carDTO.getMake(), carDTO.getModel(), carDTO.getYear(), carDTO.getPrice());
        carRepository.save(carModel);
    }

    @Transactional
    public void update(Long carID, CarDTO carDTO) {

        CarModel carModel = carRepository.getOne(carID);

        if (Objects.nonNull(carModel)) {
            if (!carDTO.getMake().isEmpty()) {
                carModel.setMake(carDTO.getMake());
            }
            if (!carDTO.getModel().isEmpty()) {
                carModel.setModel(carDTO.getModel());
            }
            if (carDTO.getPrice() > 0) {
                carModel.setPrice(carDTO.getPrice());
            }
            if (carDTO.getYear() > 0) {
                carModel.setYear(carDTO.getYear());
            }

            carRepository.save(carModel);
        }
    }
}
