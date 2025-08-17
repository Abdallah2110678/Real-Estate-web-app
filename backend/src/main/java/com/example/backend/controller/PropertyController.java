package com.example.backend.controller;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.Property;
import com.example.backend.service.PropertyService;
import com.example.backend.service.PropertyViewService;

import javax.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*")
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private PropertyViewService propertyViewService;
    
    
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Property.PropertyType type,
            @RequestParam(required = false) Property.PropertyStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<Property> properties = propertyService.searchProperties(location, type, status, minPrice, maxPrice);
        return ResponseEntity.ok(properties);
    }
    
  
    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Integer id, HttpServletRequest request) {
        Property property = propertyService.getPropertyById(id);
        if (property != null) {
            // Record view
            String ipAddress = request.getRemoteAddr();
            propertyViewService.recordView(id, ipAddress);
            return ResponseEntity.ok(property);
        }
        return ResponseEntity.notFound().build();
    }
    
  
    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.saveProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Integer id, @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Integer id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
