package org.zurika.healthappointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get all appointments for a patient
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Get all appointments for a doctor
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Schedule a new appointment
    public Appointment scheduleAppointment(Long doctorId, String appointmentDate) {
        Appointment newAppointment = new Appointment(doctorId, appointmentDate);
        return appointmentRepository.save(newAppointment);
    }

    // Update appointment status
    public void updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    // Get appointment details by ID
    public Appointment getAppointmentDetails(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }
}
