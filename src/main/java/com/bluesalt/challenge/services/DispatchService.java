package com.bluesalt.challenge.services;

import com.bluesalt.challenge.domain.DroneRequest;
import com.bluesalt.challenge.domain.dto.Response;
import com.bluesalt.challenge.exceptions.ChallengeException;
import com.bluesalt.challenge.models.Drone;
import com.bluesalt.challenge.models.Medication;
import com.bluesalt.challenge.repositories.DroneRepository;
import com.bluesalt.challenge.repositories.MedicationRepository;
import com.bluesalt.challenge.utils.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private final ResponseUtils responseUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final DroneRepository droneRepository;

    private final MedicationRepository medicationRepository;

    public ResponseEntity<Response> registerDrone(DroneRequest droneRequest) {

        Optional<Drone> droneOptional = droneRepository.findBySerialNumber(droneRequest.getSerialNumber());

        if (droneOptional.isPresent()) {
            throw new ChallengeException("Serial Number already present", HttpStatus.BAD_REQUEST);
        }

        Drone drone = objectMapper.convertValue(droneRequest, Drone.class);

        Drone davedDrone = droneRepository.save(drone);

        return responseUtils.getResponse(true, "Drone registered successfully", davedDrone);

    }

    public ResponseEntity<Response> loadDrone(String serialNumber, String medicationCode) {
        Optional<Drone> droneOptional = droneRepository.findBySerialNumber(serialNumber);
        if (droneOptional.isEmpty()) {
            throw new ChallengeException("Drone not found", HttpStatus.NOT_FOUND);
        }

        Optional<Medication> medicationOptional = medicationRepository.findByCode(medicationCode);
        if (medicationOptional.isEmpty()) {
            throw new ChallengeException("Medication not found", HttpStatus.NOT_FOUND);
        }

        Medication medication = medicationOptional.get();
        Drone drone = droneOptional.get();


        if ( medication.getWeight() > drone.getWeightLimit()) {
            throw new ChallengeException("Drone cannot carry this medication due to weight limit", HttpStatus.EXPECTATION_FAILED);
        }

        if (drone.getBatteryCapacity() < 25) {
            throw new ChallengeException("Drone's battery level is below 25%, cannot load medications", HttpStatus.EXPECTATION_FAILED);
        }

        medication.setDrone(drone);

        drone.setState(Drone.DroneState.LOADED);

        drone = droneRepository.save(drone);

        medicationRepository.save(medication);

        return responseUtils.getResponse(true, "Medication loaded onto the drone successfully", drone);
    }

    public ResponseEntity<Response> getLoadedMedications(String serialNumber) {
        Optional<Drone> droneOptional = droneRepository.findBySerialNumber(serialNumber);
        if (droneOptional.isEmpty()) {
            throw new ChallengeException("Drone not found", HttpStatus.NOT_FOUND);
        }

        Drone drone = droneOptional.get();
        return responseUtils.getResponse(true, "Medications", drone);
    }

    public ResponseEntity<Response> getAvailableDrones() {
        List<Drone> drones = droneRepository.findAll();
        return responseUtils.getResponse(true, "Drones", drones);
    }

    public ResponseEntity<Response> getDroneBattery(String serialNumber) {
        Optional<Drone> droneOptional = droneRepository.findBySerialNumber(serialNumber);
        if (droneOptional.isEmpty()) {
            throw new ChallengeException("Drone not found", HttpStatus.NOT_FOUND);
        }
        Drone drone = droneOptional.get();
        return responseUtils.getResponse(true, "Drone battery level is " + drone.getBatteryCapacity() + "%", droneOptional.get());
    }

}
