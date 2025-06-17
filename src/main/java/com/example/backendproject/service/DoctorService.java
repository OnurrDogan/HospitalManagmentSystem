package com.example.backendproject.service;

import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.repository.DoctorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorDTO(d.getId(), d.getUsername(), d.getName(), d.getSpecialty(), d.getContactNumber()))
                .collect(Collectors.toList());
    }

    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username).orElse(null);
    }

    /**
     * Verify doctor credentials using the stored bcrypt password.
     *
     * @param username provided username
     * @param rawPassword provided raw password
     * @return true if credentials match, false otherwise
     */
    public boolean authenticate(String username, String rawPassword) {
        Doctor doctor = findByUsername(username);
        return doctor != null && passwordEncoder.matches(rawPassword, doctor.getPassword());
    }
}
