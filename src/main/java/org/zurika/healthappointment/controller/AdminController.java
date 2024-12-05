package org.zurika.healthappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.healthappointment.service.*;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ReportService reportService;

    /**
     * Display the admin dashboard.
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("reportTypes", new String[]{"appointments-by-patient", "appointments-by-doctor"});
        return "admin-dashboard"; // Ensure this matches the Thymeleaf template
    }

    /**
     * Add a new user.
     */
    @PostMapping("/admin/addUser")
    public String addUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String role
                          ) {
        userService.addUser(username, email, password, firstName, lastName, role);
        return "redirect:/admin/dashboard";
    }

    /**
     * Delete a user by their ID.
     */
    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard";
    }

    /**
     * Generate a report based on the selected type.
     */
    @PostMapping("/admin/generateReport")
    public String generateReport(@RequestParam String reportType, Model model) {
        try {
            String report = reportService.generateReport(reportType);
            model.addAttribute("report", report);
            model.addAttribute("reportType", reportType);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid report type selected: " + reportType);
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("reportTypes", new String[]{"appointments-by-patient", "appointments-by-doctor"});
        return "admin-dashboard"; // Ensure this matches the Thymeleaf template
    }
}
