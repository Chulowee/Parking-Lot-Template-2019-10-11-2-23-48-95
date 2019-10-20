package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Order createOrder(Order order) {
        order.setCreationTime(formatter.format(calendar.getTime()));
        order.setCloseTime(null);
        order.setOrderStatus("Open");
        return orderRepo.save(order);
    }

    public Optional<Order> findByNameContaining(String name) {
        return orderRepo.findByNameContaining(name);
    }
}
