package com.example.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TestUsersSeeder {

    @Bean
    CommandLineRunner insertTestUsers(UserRepo users, PasswordEncoder encoder) {
        return args -> {
            createIfMissing(users, encoder,
                    "admin@site.com", "Admin", "Admin@123", Role.ADMIN);

            createIfMissing(users, encoder,
                    "agent1@site.com", "Agent One", "Agent@123", Role.AGENT);

            System.out.println("✅ Test users ready: admin@site.com / Admin@123, agent1@site.com / Agent@123");
        };
    }

    private void createIfMissing(UserRepo users,
            PasswordEncoder encoder,
            String email, String name,
            String rawPassword, Role role) {
        String normalized = email.trim().toLowerCase();
        users.findByEmail(normalized).orElseGet(() -> {
            User u = new User();
            u.setName(name);
            u.setEmail(normalized);
            u.setPassword(encoder.encode(rawPassword)); // BCrypt
            u.setRole(role);
            return users.save(u);
        });
    }
}
