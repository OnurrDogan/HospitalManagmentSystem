package controllers;

import DTOs.AppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.AppointmentService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/list";
    }

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointmentDTO", new AppointmentDTO());
        return "appointments/form";
    }

    @PostMapping
    public String saveAppointment(@ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO) {
        appointmentService.saveAppointment(appointmentDTO);
        return "redirect:/appointments";
    }
}
