package com.example.backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 150, nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Column(length = 100, nullable = false)
    private String location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType type;
    
    private Integer area;
    
    private Integer bedrooms;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;
    
    @Column(name = "agent_id", nullable = false)
    private Integer agentId;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    public enum PropertyType {
        apartment, villa, commercial, land
    }
    
    public enum PropertyStatus {
        available, sold
    }

      @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PropertyImage> images;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PropertyFile> files;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PropertyView> views;
}