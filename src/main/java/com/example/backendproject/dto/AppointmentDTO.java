package com.example.backendproject.dto;

import com.example.backendproject.model.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")   // saniye opsiyonel
    private LocalDateTime appointmentTime;

    @NotNull
    private Long doctorId;

    private Long patientId;        // servis dolduracak
    private AppointmentStatus status = AppointmentStatus.PENDING;
    private String notes;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, LocalDateTime appointmentTime, Long doctorId, Long patientId,
                          AppointmentStatus status, String notes) {
        this.id = id;
        this.appointmentTime = appointmentTime;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
