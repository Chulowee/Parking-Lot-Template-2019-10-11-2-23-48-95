package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepo;
import com.thoughtworks.parking_lot.repository.ParkingLotsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ParkingLotsRepo parkingLotRepository;

    private static final String OPEN_STATUS = "Open";
    public Order createOrder(String name, String plateNumber) {
        ParkingLot parkingLot = parkingLotRepository.findByNameContaining(name);
            if (parkingLot.getCapacity() == 0) {
                return null;
            }
            reduceCapacity(parkingLot);
            return orderRepository.save(buildOrder(name, plateNumber));
        }

    private void reduceCapacity(ParkingLot parkingLot) {
        int capacity = parkingLot.getCapacity() - 1;
        parkingLot.setCapacity(capacity);
        parkingLotRepository.save(parkingLot);
    }

    private Order buildOrder(String name, String plateNumber) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setParkingLotName(name);
        order.setPlateNumber(plateNumber);
        order.setCreationTime(String.valueOf(System.currentTimeMillis()));
        order.setOrderStatus(OPEN_STATUS);
        order.setCloseTime(null);

        return order;
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }

    public Order updateOrder(String name) {
        Optional<Order> order = orderRepository.findByNameContaining(name);
        if (isNull(order)){
            return null;
        }
        Order modifyOrder = order.get();
        modifyOrder.setCloseTime("capacity");
        modifyOrder.setOrderStatus("Closed");
        orderRepository.save(modifyOrder);
        return modifyOrder;
    }
}
