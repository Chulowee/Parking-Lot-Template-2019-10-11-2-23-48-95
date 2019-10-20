package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class ParkingLotService {
    @Autowired
    private ParkingLotsRepo parkingLotRepository;

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }

    public Iterable<ParkingLot> findAll(PageRequest name) {
        return parkingLotRepository.findAll(name);
    }
}
