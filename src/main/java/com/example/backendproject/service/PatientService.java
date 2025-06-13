package com.example.backendproject.service;

import com.example.backendproject.dto.PatientDTO;
import com.example.backendproject.model.Patient;
import com.example.backendproject.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientDTO registerPatient(PatientDTO dto) {
        Patient patient = new Patient(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getAge(), dto.getContactNumber());
        Patient saved = patientRepository.save(patient);
        dto.setId(saved.getId());
        dto.setPassword(null);
        return dto;
    }

    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username).orElse(null);
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(p -> new PatientDTO(p.getId(), p.getUsername(), p.getName(), p.getAge(), p.getContactNumber()))
                .collect(Collectors.toList());
    }
}
