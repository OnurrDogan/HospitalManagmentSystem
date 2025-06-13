package com.example.backendproject.service;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.*;
import com.example.backendproject.repository.AppointmentRepository;
import com.example.backendproject.repository.DoctorRepository;
import com.example.backendproject.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow();
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();
        if (appointmentRepository.existsByDoctorAndAppointmentTime(doctor, dto.getAppointmentTime())) {
            throw new IllegalStateException("Appointment slot taken");
        }
        Appointment appointment = new Appointment(dto.getAppointmentTime(), doctor, patient);
        Appointment saved = appointmentRepository.save(appointment);
        return toDto(saved);
    }

    public List<AppointmentDTO> getAppointmentsForPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsForDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AppointmentDTO updateStatus(Long id, AppointmentStatus status, String notes) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setStatus(status);
        if (notes != null) {
            appointment.setNotes(notes);
        }
        return toDto(appointmentRepository.save(appointment));
    }

    private AppointmentDTO toDto(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getAppointmentTime(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }
}
