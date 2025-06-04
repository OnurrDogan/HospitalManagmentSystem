package services;

import DTOs.AppointmentDTO;
import models.Appointment;
import models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AppointmentRepository;
import repositories.PatientRepository;
import repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<AppointmentDTO> getAppointmentById(int id) {
        return appointmentRepository.findById(id).map(this::toDto);
    }

    public void saveAppointment(AppointmentDTO appointmentDTO) {
        appointmentRepository.save(new Appointment(
                appointmentDTO.getId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                doctorRepository.findById(appointmentDTO.getDoctorId()).get(),
                patientRepository.findById(appointmentDTO.getPatientId()).get(),
                appointmentDTO.getNotes(),
                appointmentDTO.getStatus()
        ));
    }

    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentDTO toDto(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }
}
