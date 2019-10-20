package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @DeleteMapping(value = "/{name}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity deleteParkingLot(@PathVariable String name){
        Optional<ParkingLot> isDeleted = parkingLotService.findByNameContaining(name);
        if (isNull(isDeleted)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingLot> listOfParkingLots(
            @RequestParam(required = false , defaultValue = "0")  Integer page ,
            @RequestParam(required = false , defaultValue = "15")Integer size ) {
        return parkingLotService.findAll(PageRequest.of(page,size, Sort.by("name").ascending()));
    }
}
