package com.bluesalt.challenge.scheduler;

import com.bluesalt.challenge.models.BatteryLevelHistory;
import com.bluesalt.challenge.models.Drone;
import com.bluesalt.challenge.repositories.BatteryLevelHistoryRepository;
import com.bluesalt.challenge.repositories.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BatteryLevelChecker {

    private final DroneRepository droneRepository;
    private final BatteryLevelHistoryRepository batteryLevelHistoryRepository;
    @Scheduled(fixedRate = 60000)//Evrry minute
    public void checkBatteryLevels() {
        // Get a list of all drones from the database
        List<Drone> drones = droneRepository.findAll();

        // Iterate through each drone and check its battery level
        for (Drone drone : drones) {
            double batteryLevel = drone.getBatteryCapacity();
            String droneSerialNumber = drone.getSerialNumber();

            // Create a history/audit event log for the battery level
            BatteryLevelHistory history = new BatteryLevelHistory();
            history.setDroneSerialNumber(droneSerialNumber);
            history.setBatteryLevel(batteryLevel);
            history.setTimestamp(LocalDateTime.now());

            // Save the battery level history in the database
            batteryLevelHistoryRepository.save(history);
        }
    }
}
