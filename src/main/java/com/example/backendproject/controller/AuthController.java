package com.example.backendproject.controller;

import com.example.backendproject.dto.PatientDTO;
import com.example.backendproject.service.DoctorService;
import com.example.backendproject.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AuthController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }
    // Register a new patient
    @PostMapping("/register")
    public ResponseEntity<PatientDTO> register(@RequestBody PatientDTO dto) {
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    // Login for both patients and doctors
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PatientDTO dto) {
        boolean ok = patientService.authenticate(dto.getUsername(), dto.getPassword()) ||
                doctorService.authenticate(dto.getUsername(), dto.getPassword());
        if (ok) {
            return ResponseEntity.ok("Login successful for user: " + dto.getUsername());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
