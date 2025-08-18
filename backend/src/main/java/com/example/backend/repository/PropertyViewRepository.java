package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.backend.model.PropertyView;


@Repository
public interface PropertyViewRepository extends JpaRepository<PropertyView, Integer> {
    Long countByPropertyId(Integer propertyId);
}