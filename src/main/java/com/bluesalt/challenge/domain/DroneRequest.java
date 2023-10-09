package com.bluesalt.challenge.domain;

import lombok.Data;

@Data
public class DroneRequest {
    private String serialNumber;
    private String model;
    private Double weightLimit;
    private Integer batteryCapacity;
    private String state;
}
