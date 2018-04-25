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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        } else {
            // saving car
            carService.saveCar(carDTO);

            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
}
