package com.example.backend.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Property;
import com.example.backend.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {
    private final PropertyRepository repo;

    @GetMapping("/by-location")
    public List<Map<String, Object>> byLocation() {
        return repo.countByLocation();
    }

    @GetMapping("/avg-price-by-type")
    public List<Map<String, Object>> avgPriceByType() {
        return repo.avgPriceByType();
    }

    @GetMapping("/added-last-30-days")
    public List<Property> last30() {
        return repo.addedSince(Instant.now().minus(30, ChronoUnit.DAYS));
    }
}
