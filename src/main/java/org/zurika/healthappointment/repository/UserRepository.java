package org.zurika.healthappointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zurika.healthappointment.model.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if a user with a specific username exists
    boolean existsByUsername(String username);

    // Check if a user with a specific email exists
    boolean existsByEmail(String email);
}
