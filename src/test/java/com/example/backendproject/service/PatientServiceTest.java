package com.example.backendproject.service;

import com.example.backendproject.dto.PatientDTO;
import com.example.backendproject.model.Patient;
import com.example.backendproject.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void registerPatient_savesAndReturnsDto() {
        PatientDTO dto = new PatientDTO();
        dto.setUsername("john");
        dto.setPassword("secret");
        String originalPassword = dto.getPassword();
        dto.setName("John Doe");
        dto.setAge(25);
        dto.setContactNumber("111111");

        // mimic JPA generated id
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient p = invocation.getArgument(0);
            try {
                Field f = Patient.class.getDeclaredField("id");
                f.setAccessible(true);
                f.set(p, 1L);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return p;
        });

        PatientDTO result = patientService.registerPatient(dto);

        ArgumentCaptor<Patient> captor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(captor.capture());
        Patient patientArg = captor.getValue();
        assertEquals(dto.getUsername(), patientArg.getUsername());
        assertEquals(originalPassword, patientArg.getPassword());
        assertEquals(1L, result.getId());
        assertNull(result.getPassword());
    }
}
