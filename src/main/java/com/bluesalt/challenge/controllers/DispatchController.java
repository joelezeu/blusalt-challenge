package com.bluesalt.challenge.controllers;

import com.bluesalt.challenge.domain.DroneRequest;
import com.bluesalt.challenge.services.DispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
