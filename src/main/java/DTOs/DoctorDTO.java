package DTOs;

import java.util.List;

public class DoctorDTO {
    private String name;
    private String specialty;
    private String contactNumber;
    private List<AppointmentDTO> appointments;

    public DoctorDTO(String name, String specialty, String contactNumber, List<AppointmentDTO> appointments) {
        this.name = name;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.appointments = appointments;
    }

    public DoctorDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

}
