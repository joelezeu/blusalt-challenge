package com.bluesalt.challenge.repositories;

import com.bluesalt.challenge.models.BatteryLevelHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryLevelHistoryRepository extends JpaRepository<BatteryLevelHistory, Long> {
}
