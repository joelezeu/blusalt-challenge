package com.bluesalt.challenge.controllers;

import com.bluesalt.challenge.domain.dto.Response;
import com.bluesalt.challenge.exceptions.ChallengeException;
import com.bluesalt.challenge.models.Drone;
import com.bluesalt.challenge.models.Medication;
import com.bluesalt.challenge.repositories.DroneRepository;
import com.bluesalt.challenge.repositories.MedicationRepository;
import com.bluesalt.challenge.services.DispatchService;
import com.bluesalt.challenge.utils.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class DispatchControllerLoadDroneTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DispatchService dispatchService;

    @MockBean
    private DroneRepository droneRepository;

    @MockBean
    private MedicationRepository medicationRepository;

    @Autowired
    private ResponseUtils responseUtils;

    private final static String MEDICATION_CODE = "MEDICATION_CODE_123";
    private final static String SERIAL_NUMBER_CODE = "SERIAL_NUMBER_CODE_123";
    @BeforeEach
    public void setUp() {

        Drone drone = new Drone();
        drone.setState(Drone.DroneState.IDLE);
        drone.setModel(Drone.DroneModel.Heavyweight);
        drone.setSerialNumber("1234");
        drone.setBatteryCapacity(25);
        drone.setWeightLimit(20.0);

        // Initialize and save medication entities
        Medication medication = new Medication();
        medication.setCode("CODE123");
        medication.setName("NAME");
        medication.setDrone(drone);
        medication.setWeight(300.0);
        medication.setImage("my_image_path");
        // Mock the behavior of dispatchService, droneRepository, and medicationRepository
        when(dispatchService.loadDrone(anyString(), anyString()))
                .thenAnswer((invocation) -> {
                    String serialNumber = invocation.getArgument(0);
                    String medicationCode = invocation.getArgument(1);

                    if ("validSerialNumber".equals(serialNumber) && "validMedicationCode".equals(medicationCode)) {

                        return responseUtils.getResponse(true, "Medication loaded onto the drone successfully");
                    } else {
                        throw new ChallengeException("Some error message", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                });

        when(droneRepository.findBySerialNumber(MEDICATION_CODE))
                .thenReturn(Optional.of(drone));

        when(medicationRepository.findByCode(SERIAL_NUMBER_CODE))
                .thenReturn(Optional.of(medication));
    }

    @Test
    public void testLoadDroneWithInvalidSerialNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/drones/"+SERIAL_NUMBER_CODE+"/load/"+MEDICATION_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Some error message"));
    }

    @Test
    public void testGetLoadedMedications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/drones/"+SERIAL_NUMBER_CODE+"/loaded-medications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGetAvailableDrones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/drones/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetDroneBattery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/drones/"+SERIAL_NUMBER_CODE+"/battery")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
