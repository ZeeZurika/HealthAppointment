package org.zurika.healthappointment.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.repository.*;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    /**
     * Initialize default data on application startup.
     */
    @PostConstruct
    public void initData() {
        // Check and create default admin user if not exists
        if (userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("password"); // Replace with hashed password in production
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }

        // Check and create default doctor user if not exists
        if (userRepository.existsByUsername("doctor")) {
            User doctor = new User();
            doctor.setUsername("doctor");
            doctor.setEmail("doctor@example.com");
            doctor.setPassword("password"); // Replace with hashed password in production
            doctor.setRole(UserRole.DOCTOR);
            userRepository.save(doctor);
        }

        // Check and create default patient user if not exists
        if (userRepository.existsByUsername("patient")) {
            User patient = new User();
            patient.setUsername("patient");
            patient.setEmail("patient@example.com");
            patient.setPassword("password"); // Replace with hashed password in production
            patient.setRole(UserRole.PATIENT);
            userRepository.save(patient);
        }
    }
}
