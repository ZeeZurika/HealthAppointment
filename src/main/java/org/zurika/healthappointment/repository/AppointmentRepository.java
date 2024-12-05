package org.zurika.healthappointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zurika.healthappointment.model.Appointment;
import org.zurika.healthappointment.model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);

    // Find appointments by doctor ID
    List<Appointment> findByDoctorId(Long doctorId);

    // Find appointments by status
    List<Appointment> findByStatus(AppointmentStatus status);

    // Fetch appointments within a specific date range
    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus appointmentStatus);

    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus appointmentStatus);
}
