package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepo users;
    private final SecurityContextRepository securityContextRepository; // inject it

    public record LoginReq(@NotBlank String email, @NotBlank String password) {
    }

    public record LoginRes(java.util.UUID id, String name, String email, String role) {
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req,
            HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // Persist the context into the HTTP session (backs JSESSIONID)
        securityContextRepository.saveContext(context, request, response);

        User u = users.findByEmail(req.email()).orElseThrow();
        return ResponseEntity.ok(new LoginRes(u.getId(), u.getName(), u.getEmail(), u.getRole().toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s != null)
            s.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}