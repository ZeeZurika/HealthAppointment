package org.zurika.healthappointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.zurika.healthappointment.model.User;
import org.zurika.healthappointment.model.UserRole;
import org.zurika.healthappointment.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create a default admin user
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Hash the password
            adminUser.setFirstName("Default");
            adminUser.setLastName("Admin");
            adminUser.setRole(UserRole.ADMIN);

            userRepository.save(adminUser);

            System.out.println("Default admin user created. Username: admin, Password: admin123");
        } else {
            System.out.println("Users already exist in the database.");
        }
    }
}

