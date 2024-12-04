package org.zurika.healthappointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // Initial role selection screen
    @GetMapping("/")
    public String showRoleSelection() {
        return "role-selection"; // HTML for role selection
    }

    // Redirect to appropriate dashboard after role selection
    @PostMapping("/home/select-role")
    public String selectRole(@RequestParam("role") String role, Model model) {
        model.addAttribute("role", role);

        return switch (role) {
            case "PATIENT" -> "redirect:/patient/dashboard";
            case "DOCTOR" -> "redirect:/doctor/dashboard";
            case "ADMIN" -> "redirect:/admin/dashboard";
            default -> "redirect:/"; // Handle invalid role
        };
    }
}
