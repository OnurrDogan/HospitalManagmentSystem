package com.example.backendproject.dto;

public class PatientDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private int age;
    private String contactNumber;

    public PatientDTO() {}

    public PatientDTO(Long id, String username, String name, int age, String contactNumber) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
