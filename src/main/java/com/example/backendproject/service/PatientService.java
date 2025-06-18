package com.example.backendproject.service;

import com.example.backendproject.dto.PatientDTO;
import com.example.backendproject.model.Patient;
import com.example.backendproject.repository.PatientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registers a new patient by saving their details in the repository and encoding their password.
    public PatientDTO registerPatient(PatientDTO dto) {
        Patient patient = new Patient(dto.getUsername(), dto.getName(), dto.getAge(), dto.getContactNumber());
        patient.setPassword(passwordEncoder.encode(dto.getPassword()));
        Patient saved = patientRepository.save(patient);
        dto.setId(saved.getId());
        dto.setPassword(null);
        return dto;
    }

    // Retrieves a patient by their ID.
    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username).orElse(null);
    }

    // Retrieves all patients from the repository and converts them to DTOs.
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(p -> new PatientDTO(p.getId(), p.getUsername(), p.getName(), p.getAge(), p.getContactNumber()))
                .collect(Collectors.toList());
    }

    //Authenticates a patient by checking if the provided username exists and if the raw password matches the stored password.
    public boolean authenticate(String username, String rawPassword) {
        Patient patient = findByUsername(username);
        return patient != null && passwordEncoder.matches(rawPassword, patient.getPassword());
    }
}
