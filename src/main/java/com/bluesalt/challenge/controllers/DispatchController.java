package com.bluesalt.challenge.controllers;

import com.bluesalt.challenge.domain.DroneRequest;
import com.bluesalt.challenge.services.DispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DispatchController {

    private final DispatchService dispatchService;

    // Register a Drone
    @PostMapping("/drones")
    public ResponseEntity<?> registerDrone(@RequestBody DroneRequest drone) {
       return dispatchService.registerDrone(drone);
    }
    // Load a Drone with Medication Items
    @PostMapping("/drones/{serialNumber}/load/{medicationCode}")
    public ResponseEntity<?> loadDrone(@PathVariable String serialNumber, @PathVariable String medicationCode) {
        return dispatchService.loadDrone(serialNumber, medicationCode);
    }
    // Get Loaded Medication Items for a Given Drone
    @GetMapping("/drones/{serialNumber}/loaded-medications")
    public ResponseEntity<?> getLoadedMedications(@PathVariable String serialNumber) {
        return dispatchService.getLoadedMedications(serialNumber);
    }
}
