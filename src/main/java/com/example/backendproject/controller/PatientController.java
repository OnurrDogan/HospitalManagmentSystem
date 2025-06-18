package com.example.backendproject.controller;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.Patient;
import com.example.backendproject.service.AppointmentService;
import com.example.backendproject.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final AppointmentService appointmentService;
    private final PatientService patientService;

    public PatientController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    //Get the currently logged-in patient
    @GetMapping("/me")
    public ResponseEntity<Patient> getPatient(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Patient patient = patientService.findByUsername(principal.getName());
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(patient);
    }

    // Create a new appointment for the currently logged-in patient
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto,
                                                            Principal principal) {
        Patient p = patientService.findByUsername(principal.getName());
        dto.setPatientId(p.getId());

        AppointmentDTO created = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all appointments for the currently logged-in patient
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/appointments")
    public List<AppointmentDTO> myAppointments(Principal principal) {
        Patient patient = patientService.findByUsername(principal.getName());
        return appointmentService.getAppointmentsForPatient(patient);
    }

    // Cancel an appointment for the currently logged-in patient
    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id, Principal principal) {
        Patient patient = patientService.findByUsername(principal.getName());
        try {
            appointmentService.deleteAppointmentForPatient(id, patient);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
