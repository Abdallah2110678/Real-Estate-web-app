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
public interface PropertyRepository extends JpaRepository<Property, Long> {

  List<Property> findByAgentId(Long agentId);

  @Query("SELECT p FROM Property p WHERE " +
      "(:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
      "(:type IS NULL OR p.type = :type) AND " +
      "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
      "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
      "(:minArea IS NULL OR p.area >= :minArea) AND " +
      "(:maxArea IS NULL OR p.area <= :maxArea) AND " +
      "(:bedrooms IS NULL OR p.bedrooms = :bedrooms) AND " +
      "(:status IS NULL OR p.status = :status)")
  Page<Property> findWithFilters(@Param("location") String location,
      @Param("type") Property.PropertyType type,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("minArea") Integer minArea,
      @Param("maxArea") Integer maxArea,
      @Param("bedrooms") Integer bedrooms,
      @Param("status") Property.PropertyStatus status,
      Pageable pageable);

  @Query("SELECT COUNT(p) FROM Property p WHERE p.createdAt >= :date")
  Long countPropertiesAddedSince(@Param("date") LocalDateTime date);

  @Query("SELECT p.location, COUNT(p) FROM Property p GROUP BY p.location")
  List<Object[]> countPropertiesByLocation();

  @Query("SELECT p.type, AVG(p.price) FROM Property p GROUP BY p.type")
  List<Object[]> averagePriceByType();
}
