package com.example.backendproject.controller;

import com.example.backendproject.dto.PatientDTO;
import com.example.backendproject.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PatientService patientService;

    public AuthController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatientDTO> register(@RequestBody PatientDTO dto) {
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PatientDTO dto) {
        return ResponseEntity.ok("Login successful for user: " + dto.getUsername());
    }
}
