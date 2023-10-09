package com.bluesalt.challenge.services;

import com.bluesalt.challenge.domain.DroneRequest;
import com.bluesalt.challenge.domain.dto.Response;
import com.bluesalt.challenge.exceptions.ChallengeException;
import com.bluesalt.challenge.models.Drone;
import com.bluesalt.challenge.repositories.DroneRepository;
import com.bluesalt.challenge.utils.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private final ResponseUtils responseUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final DroneRepository droneRepository;
    public ResponseEntity<Response> registerDrone(DroneRequest droneRequest){

        Optional<Drone> droneOptional = droneRepository.findBySerialNumber(droneRequest.getSerialNumber());

        if(droneOptional.isPresent()){
            throw new ChallengeException("Serial Number already present", HttpStatus.BAD_REQUEST);
        }

        Drone drone = objectMapper.convertValue(droneRequest, Drone.class);

        Drone davedDrone = droneRepository.save(drone);

        return responseUtils.getResponse(true, "Drone registered successfully", davedDrone);

    }

}
