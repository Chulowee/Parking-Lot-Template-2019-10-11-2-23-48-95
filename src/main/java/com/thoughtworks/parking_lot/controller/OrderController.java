package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.OrderService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/{name}/Order",produces = APPLICATION_JSON_VALUE)
    public HttpEntity saveOrder(@RequestParam String plateNumber) {
        Order isSaved = orderService.save(plateNumber);
        if (isNull(isSaved)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isSaved, HttpStatus.CREATED);
    }

}
