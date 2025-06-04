package DTOs;

import models.Appointment;

import java.sql.Time;
import java.util.Date;


public class AppointmentDTO {
    private int id;
    private Date date;
    private Time time;
    private int doctorId;
    private int patientId;
    private Appointment.Status status;
    private String notes;

    public AppointmentDTO(int id, Date date, Time time, int doctorId, int patientId , Appointment.Status status, String notes) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.status = status;
        this.notes = notes;
    }

    public AppointmentDTO() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Appointment.Status getStatus() {
        return status;
    }

    public void setStatus(Appointment.Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
