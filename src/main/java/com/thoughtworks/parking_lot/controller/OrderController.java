package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/parkingLot/{name}/order")
public class OrderController {
    private static final String THE_PARKING_LOT_IS_FULL = "The parking lot is full!";
    @Autowired
    private OrderService orderService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity createOrder(@PathVariable final String name,
                                  @RequestParam(required = false, defaultValue = "") String plateNumber){
        Order isCreated = orderService.createOrder(name, plateNumber);
        if (isNull(isCreated)) {
            return new ResponseEntity<>(THE_PARKING_LOT_IS_FULL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{name}")
    public HttpEntity patchOrder(@PathVariable String name){
        Order order = orderService.updateOrder(name);
        if (isNull(order)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}