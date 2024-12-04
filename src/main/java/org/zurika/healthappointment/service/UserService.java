package org.zurika.healthappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zurika.healthappointment.model.User;
import org.zurika.healthappointment.model.UserRole;
import org.zurika.healthappointment.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Fetch a user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // Add a new user
    public User addUser(String username, String email, String password, String role) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password); // Ideally, use a PasswordEncoder to hash the password
        newUser.setRole(UserRole.valueOf(role.toUpperCase()));
        return userRepository.save(newUser);
    }

    // Delete a user
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Update personal information for a patient
    public void updatePatientInfo(Long userId, String firstName, String lastName, String email) {
        User user = getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
    }
}
