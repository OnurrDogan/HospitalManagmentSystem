package services;

import DTOs.DoctorDTO;
import models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.map(this::convertToDTO).orElse(null);
    }

    public void saveDoctor(DoctorDTO doctorDTO) {
        doctorRepository.save(new Doctor(
                doctorDTO.getName(),
                doctorDTO.getSpecialty(),
                doctorDTO.getContactNumber(),
                doctorDTO.getAppointments()
        ));
    }

    public void deleteDoctor(int id) {
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        return null;
    }
}
