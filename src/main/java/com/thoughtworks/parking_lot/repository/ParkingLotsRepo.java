package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotsRepo extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByNameContaining(String name) ;
}
