package org.zurika.healthappointment;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.repository.*;
import org.zurika.healthappointment.service.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {
        List<Appointment> mockAppointments = List.of(
                new Appointment(),
                new Appointment()
        );

        when(appointmentRepository.findAll()).thenReturn(mockAppointments);

        List<Appointment> appointments = appointmentService.getAllAppointments();
        assertNotNull(appointments);
        assertEquals(2, appointments.size());

        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testScheduleAppointment() {
        User patient = new User();
        patient.setId(1L);

        User doctor = new User();
        doctor.setId(2L);

        LocalDateTime appointmentDate = LocalDateTime.now();

        Appointment mockAppointment = new Appointment();
        mockAppointment.setPatient(patient);
        mockAppointment.setDoctor(doctor);
        mockAppointment.setAppointmentDate(appointmentDate);
        mockAppointment.setStatus(AppointmentStatus.CONFIRMED);

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        Appointment appointment = appointmentService.scheduleAppointment(1L, 2L, appointmentDate);

        assertNotNull(appointment);
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testCancelAppointment() {
        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(1L);
        existingAppointment.setStatus(AppointmentStatus.CONFIRMED);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existingAppointment));

        appointmentService.cancelAppointment(1L);

        assertEquals(AppointmentStatus.CANCELED, existingAppointment.getStatus());
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }

    @Test
    void testRescheduleAppointment() {
        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(1L);
        existingAppointment.setAppointmentDate(LocalDateTime.now());
        existingAppointment.setStatus(AppointmentStatus.CONFIRMED);

        LocalDateTime newDate = LocalDateTime.now().plusDays(1);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existingAppointment));

        appointmentService.rescheduleAppointment(1L, newDate);

        assertEquals(newDate, existingAppointment.getAppointmentDate());
        assertEquals(AppointmentStatus.RESCHEDULED, existingAppointment.getStatus());
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }
}

