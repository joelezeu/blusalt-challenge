package com.bluesalt.challenge.services;

import com.bluesalt.challenge.models.Drone;
import com.bluesalt.challenge.models.Medication;
import com.bluesalt.challenge.repositories.DroneRepository;
import com.bluesalt.challenge.repositories.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeMedications();
    }

    private void initializeMedications() {

        Drone drone = new Drone();
        drone.setState("IDLE");
        drone.setModel("NEW_MOBEL");
        drone.setSerialNumber("1234");
        drone.setBatteryCapacity(25);
        drone.setWeightLimit(20.0);

        drone = droneRepository.save(drone);


        // Initialize and save medication entities
        Medication medication = new Medication();
        medication.setCode("CODE123");
        medication.setName("NAME");
        medication.setDrone(drone);
        medication.setWeight(300.0);
        medication.setImage("my_image_path");
        medicationRepository.save(medication);

    }
}
