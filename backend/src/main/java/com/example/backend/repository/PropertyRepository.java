package com.example.backend.repository;

import com.example.backend.model.Property;

import java.time.Instant;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long> { @Query("select p.location as location, count(p) as cnt from Property p group by p.location")
    List<Map<String, Object>> countByLocation();

    @Query("select p.type as type, avg(p.price) as avgPrice from Property p group by p.type")
    List<Map<String, Object>> avgPriceByType();

    @Query("select p from Property p where p.createdAt >= :since order by p.createdAt desc")
    List<Property> addedSince(@Param("since") Instant since);


}
