package com.wanderlust.wanderlust_app.user.persistence;

import com.wanderlust.wanderlust_app.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findByEmailOrPhone(String identifier);
    Optional<User> findByEmail(String identifier);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
