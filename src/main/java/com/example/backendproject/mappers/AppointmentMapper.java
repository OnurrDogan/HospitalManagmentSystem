package com.example.backendproject.mappers;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.Appointment;
import com.example.backendproject.model.AppointmentStatus;
import com.example.backendproject.model.Doctor;
import com.example.backendproject.model.Patient;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentDTO dto,
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

    public static AppointmentDTO toDto(Appointment a) {
        return new AppointmentDTO(
                a.getId(),
                a.getAppointmentTime(),
                a.getDoctor().getId(),
                a.getPatient().getId(),
                a.getStatus(),
                a.getNotes()
        );
    }
}
