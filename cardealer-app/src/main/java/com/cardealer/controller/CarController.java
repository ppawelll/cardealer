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

import javax.validation.Valid;


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
    public ResponseEntity insert(@Valid CarDTO carDTO, BindingResult result) {
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
            @ApiResponse(code = 500, message = "Car not saved - internal server error occurred!") })
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable("id") Long carID, @RequestBody CarDTO carDTO) {
        try {
            carService.update(carID, carDTO);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity((HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
