package com.example.backendproject.dto;

public class DoctorDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String specialty;
    private String contactNumber;

    public DoctorDTO() {}

    public DoctorDTO(Long id, String username, String name, String specialty, String contactNumber) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
