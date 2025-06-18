package com.example.backendproject.service;

import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.repository.DoctorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }


    // Retrieves all doctors from the repository and converts them to DTOs.
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorDTO(d.getId(), d.getUsername(), d.getName(), d.getSpecialty(), d.getContactNumber()))
                .collect(Collectors.toList());
    }

    //Retrieves a doctor by their ID and converts it to a DTO.
    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username).orElse(null);
    }

    // Authenticates a doctor by checking if the provided username exists and if the raw password matches the stored password.
    public boolean authenticate(String username, String rawPassword) {
        Doctor doctor = findByUsername(username);
        return doctor != null && passwordEncoder.matches(rawPassword, doctor.getPassword());
    }

    // Transforms a Doctor entity to a DoctorDTO.
    public DoctorDTO convertToDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return new DoctorDTO(doctor.getId(), doctor.getUsername(), doctor.getName(), doctor.getSpecialty(), doctor.getContactNumber());
    }
}
