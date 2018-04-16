package com.cardealer.controller;

import com.cardealer.dto.CarDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InsertController {
    @ApiOperation(value="Insert a new car", response = ResponseEntity.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully saved the car"),
        @ApiResponse(code = 500, message = "Car not saved - internal server error occurred!") })
    @RequestMapping(value={"/car/insert"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity insert(CarDTO carDTO) {
        if (carDTO != null && !carDTO.getMake().isEmpty() && !carDTO.getModel().isEmpty() && carDTO.getPrice() > 0 &&
                carDTO.getYear() > 1750) {
            // saving car
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
