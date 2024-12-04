package org.zurika.healthappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    // Patient Dashboard: View all appointments
    @GetMapping("/patient/dashboard")
    public String patientDashboard(Model model) {
        Long patientId = getLoggedInPatientId(); // Replace with actual logic to get logged-in user's ID
        List<Appointment> appointments = appointmentService.getPatientAppointments(patientId);
        model.addAttribute("appointments", appointments);
        return "patient-dashboard"; // Redirects to the patient dashboard
    }

    // Schedule a new appointment
    @PostMapping("/patient/schedule")
    public String scheduleAppointment(@RequestParam Long doctorId,
                                      @RequestParam String appointmentDate,
                                      Model model) {
        try {
            Long patientId = getLoggedInPatientId(); // Replace with actual logic to get logged-in user's ID
            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentDate);
            appointmentService.scheduleAppointment(patientId, doctorId, appointmentDateTime);
            model.addAttribute("successMessage", "Appointment scheduled successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error scheduling appointment: " + e.getMessage());
        }
        return "redirect:/patient/dashboard"; // Redirect to the patient dashboard
    }

    // Cancel an appointment
    @PostMapping("/patient/cancel")
    public String cancelAppointment(@RequestParam Long appointmentId, Model model) {
        try {
            appointmentService.cancelAppointment(appointmentId);
            model.addAttribute("successMessage", "Appointment canceled successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error canceling appointment: " + e.getMessage());
        }
        return "redirect:/patient/dashboard"; // Redirect to the patient dashboard
    }

    // Update personal information
    @PostMapping("/patient/updateInfo")
    public String updatePatientInfo(@RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String email,
                                    Model model) {
        try {
            Long patientId = getLoggedInPatientId(); // Replace with actual logic to get logged-in user's ID
            userService.updatePatientInfo(patientId, firstName, lastName, email);
            model.addAttribute("successMessage", "Information updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating information: " + e.getMessage());
        }
        return "redirect:/patient/dashboard"; // Redirect to the patient dashboard
    }

    // Helper method to get the logged-in patient's ID (stub, replace with actual implementation)
    private Long getLoggedInPatientId() {
        // Replace this with actual logic (e.g., Spring Security authentication)
        return 1L; // Example static ID for testing
    }
}
