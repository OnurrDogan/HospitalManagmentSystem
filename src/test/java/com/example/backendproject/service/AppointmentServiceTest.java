package com.example.backendproject.service;

import com.example.backendproject.dto.AppointmentDTO;
import com.example.backendproject.model.*;
import com.example.backendproject.repository.AppointmentRepository;
import com.example.backendproject.repository.DoctorRepository;
import com.example.backendproject.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void createAppointment_whenSlotTaken_throwsException() {
        LocalDateTime time = LocalDateTime.now();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsByDoctorAndAppointmentTime(doctor, time)).thenReturn(true);

        AppointmentDTO dto = new AppointmentDTO();
        dto.setDoctorId(1L);
        dto.setPatientId(2L);
        dto.setAppointmentTime(time);

        assertThrows(IllegalStateException.class, () -> appointmentService.createAppointment(dto));
    }

    @Test
    void updateStatus_updatesAppointment() {
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Appointment appointment = new Appointment(LocalDateTime.now(), doctor, patient);
        try {
            Field f = Appointment.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(appointment, 5L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(appointmentRepository.findById(5L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentDTO result = appointmentService.updateStatus(5L, AppointmentStatus.CONFIRMED, "ok");

        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertEquals("ok", appointment.getNotes());
        assertEquals(5L, result.getId());
        assertEquals(AppointmentStatus.CONFIRMED, result.getStatus());
        assertEquals("ok", result.getNotes());
    }
}
