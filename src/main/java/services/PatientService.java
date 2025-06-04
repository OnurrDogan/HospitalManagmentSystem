package services;

import DTOs.PatientDTO;
import models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public void registerPatient(PatientDTO patientDTO) {
    }

    public List<PatientDTO> getAllPatients() {
        return null;
    }

    public PatientDTO getPatientById(int id) {
        return null;
    }

    public void savePatient(PatientDTO patientDTO) {
    }

    public void deletePatient(int id) {
    }

    private PatientDTO convertToDTO(Patient patient) {
        return null;
    }
}
