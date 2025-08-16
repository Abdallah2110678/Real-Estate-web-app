package com.example.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Property;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByAgentId(Integer agentId);
    List<Property> findByLocationContaining(String location);
    List<Property> findByType(Property.PropertyType type);
    List<Property> findByStatus(Property.PropertyStatus status);
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}