package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.models.PropertyView;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PropertyViewRepository extends JpaRepository<PropertyView, Long> {

  Long countByPropertyId(Long propertyId);

  @Query("SELECT pv.property.id, COUNT(pv) FROM PropertyView pv " +
      "WHERE pv.viewedAt >= :date " +
      "GROUP BY pv.property.id " +
      "ORDER BY COUNT(pv) DESC")
  List<Object[]> findTopViewedPropertiesSince(@Param("date") LocalDateTime date);

  List<PropertyView> findByPropertyId(Long propertyId);
}