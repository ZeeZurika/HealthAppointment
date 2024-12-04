package org.zurika.healthappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.healthappointment.model.Appointment;
import org.zurika.healthappointment.model.AppointmentStatus;
import org.zurika.healthappointment.service.AppointmentService;

import java.time.LocalDateTime;

@Controller
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    // View all appointments for the logged-in doctor
    @GetMapping("/doctor/appointments")
    public String viewAppointments(Model model) {
        // Assuming the logged-in doctor’s ID is obtained from the session or security context
        Long doctorId = getLoggedInDoctorId();
        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctorId));
        return "doctor/view-appointments"; // Thymeleaf template for viewing appointments
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
        return "redirect:/doctor/appointments"; // Redirect to the appointments page
    }

    // Helper method to get the logged-in doctor's ID (stub, replace with actual implementation)
    private Long getLoggedInDoctorId() {
        // Replace this with actual logic to get the logged-in user's ID
        return 1L; // Example ID
    }
}
