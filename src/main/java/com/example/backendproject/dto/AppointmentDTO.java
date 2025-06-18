package com.example.backendproject.dto;

import com.example.backendproject.model.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")
    private LocalDateTime appointmentTime;

    @NotNull
    private Long doctorId;
    private String doctorName;
    private String policlinicName;

    private Long patientId;
    private String patientName;
    private AppointmentStatus status = AppointmentStatus.PENDING;
    private String notes;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, LocalDateTime appointmentTime, Long doctorId, String doctorName, String policlinicName, Long patientId, String patientName,AppointmentStatus status, String notes) {
        this.id = id;
        this.appointmentTime = appointmentTime;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.policlinicName = policlinicName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getPoliclinicName() { return policlinicName; }
    public void setPoliclinicName(String policlinicName) { this.policlinicName = policlinicName; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
