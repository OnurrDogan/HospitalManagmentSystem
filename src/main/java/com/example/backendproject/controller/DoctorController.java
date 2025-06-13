package com.example.backendproject.controller;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.AppointmentStatus;
import com.example.backendproject.dto.DoctorDTO;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.service.AppointmentService;
import com.example.backendproject.service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<AppointmentDTO> myAppointments(Principal principal) {
        Doctor doctor = doctorService.findByUsername(principal.getName());
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
