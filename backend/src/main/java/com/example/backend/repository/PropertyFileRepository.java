package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.PropertyFile;

import java.util.List;

@Repository
public interface PropertyFileRepository extends JpaRepository<PropertyFile, Integer> {
    List<PropertyFile> findByPropertyId(Integer propertyId);
}