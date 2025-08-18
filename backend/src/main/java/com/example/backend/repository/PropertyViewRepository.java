package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.PropertyView;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PropertyViewRepository extends JpaRepository<PropertyView, Integer> {
    Long countByPropertyId(Integer propertyId);
}