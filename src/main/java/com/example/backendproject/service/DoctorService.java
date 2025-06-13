package com.example.backendproject.service;

import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorDTO createDoctor(DoctorDTO dto) {
        Doctor doctor = new Doctor(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSpecialty(), dto.getContactNumber());
        Doctor saved = doctorRepository.save(doctor);
        dto.setId(saved.getId());
        dto.setPassword(null); // hide password
        return dto;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorDTO(d.getId(), d.getUsername(), d.getName(), d.getSpecialty(), d.getContactNumber()))
                .collect(Collectors.toList());
    }

    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username).orElse(null);
    }
}
