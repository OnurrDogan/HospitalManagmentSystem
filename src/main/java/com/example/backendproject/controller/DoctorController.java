package com.example.backendproject.controller;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.AppointmentStatus;
import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.service.AppointmentService;
import com.example.backendproject.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public DoctorController(AppointmentService appointmentService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorDTO> allDoctors() {
        return doctorService.getAllDoctors();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/appointments")
    public List<AppointmentDTO> myAppointments(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        Doctor doctor = doctorService.findByUsername(auth.getName());
        return appointmentService.getAppointmentsForDoctor(doctor);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/appointments/{id}/status")
    public AppointmentDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        AppointmentStatus status = AppointmentStatus.valueOf(body.get("status"));
        String notes = body.get("notes");
        return appointmentService.updateStatus(id, status, notes);
    }
}
