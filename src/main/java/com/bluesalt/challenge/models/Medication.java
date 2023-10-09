package com.bluesalt.challenge.models;

public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double weight;
    private String code;
    private String image;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
}
