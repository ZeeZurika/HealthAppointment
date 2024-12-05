package org.zurika.healthappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    //Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Get all doctors
    public List<User> getAllDoctors() {
        return userRepository.findByRole(UserRole.DOCTOR);
    }

    // Get all appointments for a patient
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, AppointmentStatus.CONFIRMED);
    }

    // Get all appointments for a doctor
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.CONFIRMED);
    }

    // Get all appointments by status
    public List<Appointment> getAppointmentsByStatus(String status) {
        AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        return appointmentRepository.findByStatus(AppointmentStatus.valueOf(String.valueOf(appointmentStatus)));
    }


    // Get appointments within a specific date range
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateBetween(startDate, endDate);
    }

    // Schedule a new appointment
    public Appointment scheduleAppointment(Long patientId, Long doctorId, LocalDateTime appointmentDate) {
        // Fetch patient and doctor from the database
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found!"));
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found!"));

        // Create a new appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus(AppointmentStatus.CONFIRMED); // Default to CONFIRMED when scheduled

        return appointmentRepository.save(appointment); // Save and return the appointment
    }


    // Update the status of an appointment
    public void updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found!"));
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    // Reschedule an appointment
    public void rescheduleAppointment(Long appointmentId, LocalDateTime newDate) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found!"));
        appointment.setAppointmentDate(newDate);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointmentRepository.save(appointment);
    }

    // Cancel an appointment
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found!"));
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
    }

    // Mark an appointment as completed
    public void completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found!"));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    // Retrieve appointment details by ID
    public Optional<Appointment> getAppointmentDetails(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
}
