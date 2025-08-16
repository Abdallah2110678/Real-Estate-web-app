package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.models.Property;
import com.example.backend.repository.PropertyRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService {

  @Autowired
  private PropertyRepository propertyRepository;

  public List<Property> getAllProperties() {
    return propertyRepository.findAll();
  }

  public Property getPropertyById(Integer id) {
    return propertyRepository.findById(id).orElse(null);
  }

  public Property saveProperty(Property property) {
    return propertyRepository.save(property);
  }

  public Property updateProperty(Integer id, Property property) {
    property.setId(id);
    return propertyRepository.save(property);
  }

  public void deleteProperty(Integer id) {
    propertyRepository.deleteById(id);
  }

  public List<Property> getPropertiesByAgent(Integer agentId) {
    return propertyRepository.findByAgentId(agentId);
  }

  public List<Property> searchProperties(String location, Property.PropertyType type,
      Property.PropertyStatus status,
      BigDecimal minPrice, BigDecimal maxPrice) {
    if (location != null) {
      return propertyRepository.findByLocationContaining(location);
    }
    if (type != null) {
      return propertyRepository.findByType(type);
    }
    if (status != null) {
      return propertyRepository.findByStatus(status);
    }
    if (minPrice != null && maxPrice != null) {
      return propertyRepository.findByPriceBetween(minPrice, maxPrice);
    }
    return propertyRepository.findAll();
  }
}
