package com.example.backendproject.controller;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.AppointmentStatus;
import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.service.AppointmentService;
import com.example.backendproject.service.DoctorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Get the authenticated doctor's details
    @GetMapping("/me")
    public DoctorDTO getDoctor(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        Doctor doctor = doctorService.findByUsername(auth.getName());
        return doctorService.convertToDTO(doctor);
    }


    // Get all doctors
    @GetMapping
    public List<DoctorDTO> allDoctors() {
        return doctorService.getAllDoctors();
    }

    // Get appointments for the authenticated doctor
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/appointments")
    public List<AppointmentDTO> myAppointments(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        Doctor doctor = doctorService.findByUsername(auth.getName());
        return appointmentService.getAppointmentsForDoctor(doctor);
    }

    // Get appointments for a specific doctor by ID
    @GetMapping("/{id}/appointments")
    public List<AppointmentDTO> appointmentsForDoctor(
            @PathVariable Long id,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Doctor doctor = doctorService.findById(id)  // ✅ ID ile bul
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        if (date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end   = date.plusDays(1).atStartOfDay();
            return appointmentService.getAppointmentsForDoctorBetween(doctor, start, end);
        }
        return appointmentService.getAppointmentsForDoctor(doctor);
    }



    // Update the status of an appointment
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/appointments/{id}/status")
    public AppointmentDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        AppointmentStatus status = AppointmentStatus.valueOf(body.get("status"));
        String notes = body.get("notes");
        return appointmentService.updateStatus(id, status, notes);
    }
}
