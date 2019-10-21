package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingOrder;
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
    public ParkingOrder createOrder(String name, String plateNumber) {
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

    private ParkingOrder buildOrder(String name, String plateNumber) {
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setOrderNumber(UUID.randomUUID().toString());
        parkingOrder.setParkingLotName(name);
        parkingOrder.setPlateNumber(plateNumber);
        parkingOrder.setCreationTime(String.valueOf(System.currentTimeMillis()));
        parkingOrder.setOrderStatus(OPEN_STATUS);
        parkingOrder.setCloseTime(null);

        return parkingOrder;
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }

    public ParkingOrder updateOrder(String name) {
        Optional<ParkingOrder> order = orderRepository.findByPlateNumber(name);
        if (isNull(order)){
            return null;
        }
        ParkingOrder modifyParkingOrder = order.get();
        modifyParkingOrder.setCloseTime("capacity");
        modifyParkingOrder.setOrderStatus("Closed");
        orderRepository.save(modifyParkingOrder);
        return modifyParkingOrder;
    }
}
