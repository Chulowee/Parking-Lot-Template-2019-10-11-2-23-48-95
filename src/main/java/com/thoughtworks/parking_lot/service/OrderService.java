package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Order save(String plateNumber) {
        Order order = new Order();
        order.setPlateNumber(plateNumber);
        order.setCreationTime(formatter.format(calendar.getTime()));
        order.setCloseTime(null);
        order.setOrderStatus("Open");
        return orderRepo.save(order);
    }
}
