package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/parkingLot")
public class ParkingLotContoller {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity saveParkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot isSaved = parkingLotService.save(parkingLot);
        if (isNull(isSaved)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isSaved, HttpStatus.CREATED);
    }
}
