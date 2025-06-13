package com.example.backendproject;

import com.example.backendproject.model.Doctor;
import com.example.backendproject.model.Patient;
import com.example.backendproject.repository.DoctorRepository;
import com.example.backendproject.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(DoctorRepository doctorRepo, PatientRepository patientRepo) {
        return args -> {
            if (doctorRepo.count() == 0) {
                Doctor doc = new Doctor("drsmith", "password", "Dr. Smith", "Cardiology", "123456");
                doctorRepo.save(doc);
            }
            if (patientRepo.count() == 0) {
                Patient pat = new Patient("johndoe", "password", "John Doe", 30, "555-1234");
                patientRepo.save(pat);
            }
        };
    }
}
