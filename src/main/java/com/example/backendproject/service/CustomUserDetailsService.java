package com.example.backendproject.service;

import com.example.backendproject.model.Doctor;
import com.example.backendproject.model.Patient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final DoctorService doctorService;
    private final PatientService patientService;

    public CustomUserDetailsService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorService.findByUsername(username);
        if (doctor != null) {
            return new User(doctor.getUsername(), doctor.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        }
        Patient patient = patientService.findByUsername(username);
        if (patient != null) {
            return new User(patient.getUsername(), patient.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
