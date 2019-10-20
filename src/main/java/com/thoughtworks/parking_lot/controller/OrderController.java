package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/parkingLot/{name}/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity saveOrder(@RequestBody Order order) {
        Order isSaved = orderService.createOrder(order);
        if (isNull(isSaved)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isSaved, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{name}")
    public HttpEntity patchOrder(@PathVariable String name){
        Optional<Order> order = orderService.findByNameContaining(name);
        if (isNull(order)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order modifyOrder = order.get();
        modifyOrder.setCloseTime("capacity");
        modifyOrder.setOrderStatus("Closed");
        orderService.createOrder(modifyOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}