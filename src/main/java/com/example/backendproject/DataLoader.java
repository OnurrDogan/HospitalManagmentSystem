package com.example.backendproject;

import com.example.backendproject.model.Doctor;
import com.example.backendproject.model.Patient;
import com.example.backendproject.repository.DoctorRepository;
import com.example.backendproject.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CommandLineRunner to initialize the database with some default data for doctors and patients.
    @Bean
    CommandLineRunner initData(DoctorRepository doctorRepo, PatientRepository patientRepo) {
        return args -> {
            if (doctorRepo.count() == 0) {
                Doctor doc = new Doctor("drsmith", "Dr. Smith", "Cardiology", "123456");
                doc.setPassword(passwordEncoder().encode("password"));
                doctorRepo.save(doc);

                Doctor doc2 = new Doctor("drjones", "Dr. Jones", "Neurology", "987654");
                doc2.setPassword(passwordEncoder().encode("password"));
                doctorRepo.save(doc2);

                Doctor doc3 = new Doctor("drbrown", "Dr. Brown", "Pediatrics", "555-6789");
                doc3.setPassword(passwordEncoder().encode("password"));
                doctorRepo.save(doc3);
            }
            if (patientRepo.count() == 0) {
                Patient pat = new Patient("johndoe", "John Doe", 30, "555-1234");
                pat.setPassword(passwordEncoder().encode("password"));
                patientRepo.save(pat);
            }
        };
    }
}
