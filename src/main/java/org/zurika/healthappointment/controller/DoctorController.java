package org.zurika.healthappointment.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.service.*;

import java.time.LocalDateTime;

@Controller
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User doctor = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctor.getId()));
        model.addAttribute("doctor", doctor);
        return "doctor-dashboard";
    }

    // Update the status of an appointment (e.g., Confirm, Complete, Cancel, Reschedule)
    @PostMapping("/doctor/updateAppointment")
    public String updateAppointment(@RequestParam Long appointmentId,
                                    @RequestParam String status,
                                    @RequestParam(required = false) String newDate,
                                    Model model) {
        try {
            if ("RESCHEDULED".equalsIgnoreCase(status) && newDate != null) {
                LocalDateTime rescheduleDate = LocalDateTime.parse(newDate);
                appointmentService.rescheduleAppointment(appointmentId, rescheduleDate);
            } else {
                appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.valueOf(status.toUpperCase()));
            }
            model.addAttribute("successMessage", "Appointment updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating appointment: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard"; // Redirect to the appointments page
    }

    // Helper method to get the logged-in doctor's ID (replace with actual implementation)
    private Long getLoggedInDoctorId() {
        // Replace this with actual logic to get the logged-in user's ID
        return 1L; // Example ID
    }
}

