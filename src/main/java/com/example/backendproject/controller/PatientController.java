package com.example.backendproject.controller;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.Patient;
import com.example.backendproject.service.AppointmentService;
import com.example.backendproject.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final AppointmentService appointmentService;
    private final PatientService patientService;

    public PatientController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto,
                                                            Principal principal) {
        Patient p = patientService.findByUsername(principal.getName());
        dto.setPatientId(p.getId());

        AppointmentDTO created = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/appointments")
    public List<AppointmentDTO> myAppointments(Principal principal) {
        Patient patient = patientService.findByUsername(principal.getName());
        return appointmentService.getAppointmentsForPatient(patient);
    }
}
