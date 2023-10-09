package com.bluesalt.challenge.models;


import java.util.List;

public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;
    private String model;
    private double weightLimit;
    private int batteryCapacity;
    private String state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drone")
    private List<Medication> loadedMedications;
}
