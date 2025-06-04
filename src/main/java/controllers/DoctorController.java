package controllers;

import DTOs.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.DoctorService;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/list";
    }

    @GetMapping("/new")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctorDTO", new DoctorDTO());
        return "doctors/form";
    }

    @PostMapping
    public String saveDoctor(@ModelAttribute("doctorDTO") DoctorDTO doctorDTO) {
        doctorService.saveDoctor(doctorDTO);
        return "redirect:/doctors";
    }
}
