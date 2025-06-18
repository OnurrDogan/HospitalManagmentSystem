package com.example.backendproject.repository;

import com.example.backendproject.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Using Spring Data JPA to create a repository for Doctor entity and define methods for querying doctors by username.
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);
}
