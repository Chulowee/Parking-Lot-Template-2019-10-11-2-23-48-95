package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<ParkingOrder, String> {

    Optional<ParkingOrder> findByPlateNumber(String parkingLotName) ;
}
