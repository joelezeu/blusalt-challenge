package com.bluesalt.challenge.repositories;


import com.bluesalt.challenge.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByCode(String code);
}
