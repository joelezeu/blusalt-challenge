package com.bluesalt.challenge.controllers;

import com.bluesalt.challenge.domain.DroneRequest;
import com.bluesalt.challenge.domain.dto.Response;
import com.bluesalt.challenge.repositories.DroneRepository;
import com.bluesalt.challenge.services.DispatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class DispatchControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private DispatchService dispatchService;

    @MockBean
    private DroneRepository droneRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testRegisterDrone() throws Exception {

        String serialNumber = "1234512433";
        // Create a sample DroneRequest
        DroneRequest droneRequest = new DroneRequest();
        droneRequest.setSerialNumber(serialNumber);
        // Convert the DroneRequest to JSON
        String droneRequestJson = objectMapper.writeValueAsString(droneRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(droneRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
