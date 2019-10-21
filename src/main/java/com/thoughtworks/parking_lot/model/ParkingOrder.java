package com.thoughtworks.parking_lot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String orderNumber;
    private String parkingLotName;
    private String plateNumber;
    private String creationTime;
    private String closeTime;
    private String orderStatus;

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }
}
