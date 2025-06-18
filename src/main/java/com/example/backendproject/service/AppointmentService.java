package com.example.backendproject.service;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.*;
import com.example.backendproject.repository.AppointmentRepository;
import com.example.backendproject.repository.DoctorRepository;
import com.example.backendproject.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    // Creates a new appointment for a patient with a specific doctor.
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor"));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient"));

        Appointment entity = toEntity(dto, doctor, patient);

        // Check if an appointment already exists for the doctor at the specified time
        boolean exists = appointmentRepository.existsByDoctorAndAppointmentTime(doctor, entity.getAppointmentTime());
        if (exists) throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment already exists");

        Appointment saved = appointmentRepository.save(entity);
        return toDto(saved);
    }

    // Retrieves all appointments for a specific patient.
    public List<AppointmentDTO> getAppointmentsForPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Retrieves all appointments for a specific doctor.
    public List<AppointmentDTO> getAppointmentsForDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Retrieves all appointments for a specific doctor within a given time range.
    public List<AppointmentDTO> getAppointmentsForDoctorBetween(
            Doctor doctor, LocalDateTime start, LocalDateTime end) {

        return appointmentRepository
                .findByDoctorAndAppointmentTimeBetween(doctor, start, end)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // Deletes an appointment for a specific patient.
    @Transactional
    public boolean deleteAppointmentForPatient(Long apptId, Patient patient) {
        Appointment appt = appointmentRepository.findById(apptId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Randevu o hastaya mı ait?
        if (!appt.getPatient().getId().equals(patient.getId())) {
            throw new AccessDeniedException("Appointment does not belong to patient");
        }

        appointmentRepository.delete(appt);
        return true;
    }

    // Updates the status of an appointment and optionally adds notes.
    public AppointmentDTO updateStatus(Long id, AppointmentStatus status, String notes) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setStatus(status);
        if (notes != null) {
            appointment.setNotes(notes);
        }
        return toDto(appointmentRepository.save(appointment));
    }

    // Transforms an Appointment entity to an AppointmentDTO.
    private AppointmentDTO toDto(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getAppointmentTime(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialty(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }

    // Transforms an AppointmentDTO to an Appointment entity.
    private static Appointment toEntity(AppointmentDTO dto,
                                       Doctor doctor,
                                       Patient patient) {
        Appointment a = new Appointment();
        a.setAppointmentTime(dto.getAppointmentTime());
        a.setDoctor(doctor);
        a.setPatient(patient);
        a.setStatus(dto.getStatus() != null ? dto.getStatus()
                : AppointmentStatus.PENDING);
        a.setNotes(dto.getNotes());
        return a;
    }
}
