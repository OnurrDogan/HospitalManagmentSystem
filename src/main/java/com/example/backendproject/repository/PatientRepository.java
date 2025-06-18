package com.example.backendproject.repository;

import com.example.backendproject.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Using Spring Data JPA to create a repository for Patient entity and define methods for querying patients by username.
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUsername(String username);
}
