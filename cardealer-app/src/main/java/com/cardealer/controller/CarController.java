package com.cardealer.controller;

import com.cardealer.dto.CarDTO;
import com.cardealer.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value={"/car"})
public class CarController {
    @Autowired
    private CarService carService;


    @ApiOperation(value="Insert a new car", response = ResponseEntity.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully saved the car"),
        @ApiResponse(code = 500, message = "Car not saved - internal server error occurred!") })
    @RequestMapping(value={"/insert"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity insert(@RequestBody @Valid CarDTO carDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity((HttpStatus.INTERNAL_SERVER_ERROR));
        }

        // saving car
        try {
            carService.saveCar(carDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity((HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @ApiOperation(value="Update the car", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Car updated successfully."),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Car not saved - internal server error occurred!") })
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable("id") Long carID, @RequestBody CarDTO carDTO) {
        try {
            carService.update(carID, carDTO);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Find all cars", notes = "Retrieving the collection of cars", response = CarDTO[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CarDTO[].class)
    })
    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CarDTO> findAll() {
        return carService.findAll();
    }
}
