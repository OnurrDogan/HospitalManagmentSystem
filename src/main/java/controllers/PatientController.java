package controllers;

import DTOs.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/list";
    }

    @GetMapping("/new")
    public String showPatientForm(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        return "patients/form";
    }

    @PostMapping
    public String savePatient(@ModelAttribute("patientDTO") PatientDTO patientDTO) {
        patientService.savePatient(patientDTO);
        return "redirect:/patients";
    }
}
