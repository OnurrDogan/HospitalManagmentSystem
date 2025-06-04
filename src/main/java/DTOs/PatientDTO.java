package DTOs;

import java.util.List;

public class PatientDTO {
    private String name;
    private int age;
    private String contactNumber;
    private List<AppointmentDTO> appointments;

    public PatientDTO(String name, int age, String contactNumber, List<AppointmentDTO> appointments) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.appointments = appointments;
    }

    public PatientDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
