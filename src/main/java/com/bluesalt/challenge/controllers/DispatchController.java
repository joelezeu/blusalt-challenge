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
    public ResponseEntity<String> loadDrone(@PathVariable String serialNumber, @PathVariable String medicationCode) {
        return dispatchService.loadDrone(serialNumber, medicationCode);
    }
}
