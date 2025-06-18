package com.example.backendproject.repository;

import com.example.backendproject.model.Appointment;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

//Using Spring Data JPA to create a repository for Appointment entity and define methods for querying appointments by doctor and patient, as well as checking for appointment conflicts.
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    boolean existsByDoctorAndAppointmentTime(Doctor doctor, LocalDateTime time);
    List<Appointment> findByDoctorAndAppointmentTimeBetween(
            Doctor doctor, LocalDateTime start, LocalDateTime end);

}
