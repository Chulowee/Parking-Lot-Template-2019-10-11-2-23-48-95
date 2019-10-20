package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
