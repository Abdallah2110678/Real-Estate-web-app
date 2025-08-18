package com.example.backend.service;

import com.example.backend.model.Property;
import com.example.backend.model.dto.PropertyCreateRequest;
import com.example.backend.model.dto.PropertyUpdateRequest;
import com.example.backend.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class PropertyService {

    private final PropertyRepository repo;

    public PropertyService(PropertyRepository repo) {
        this.repo = repo;
    }

    public List<Property> findAll() {
        return repo.findAll();
    }

    public Property findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Property not found: " + id));
    }

    public Property create(PropertyCreateRequest req) {
        Property p = new Property();
        copyCreate(req, p);
        return repo.save(p);
    }

    public Property updateProperty(Long id, PropertyUpdateRequest request) {
        Property existingProperty = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (request.getTitle() != null) {
            existingProperty.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            existingProperty.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            existingProperty.setPrice(request.getPrice());
        }
        if (request.getLocation() != null) {
            existingProperty.setLocation(request.getLocation());
        }
        if (request.getType() != null) {
            existingProperty.setType(request.getType());
        }
        if (request.getArea() != null) {
            existingProperty.setArea(request.getArea());
        }
        if (request.getBedrooms() != null) {
            existingProperty.setBedrooms(request.getBedrooms());
        }
        if (request.getStatus() != null) {
            existingProperty.setStatus(request.getStatus());
        }

        return repo.save(existingProperty);
    }

    public void delete(Long id) {
        Property p = findById(id);
        repo.delete(p);
    }

    private void copyCreate(PropertyCreateRequest src, Property dest) {
        dest.setTitle(src.getTitle());
        dest.setDescription(src.getDescription());
        dest.setPrice(src.getPrice());
        dest.setLocation(src.getLocation());
        dest.setType(src.getType());
        dest.setArea(src.getArea());
        dest.setBedrooms(src.getBedrooms());
        dest.setStatus(src.getStatus());
        dest.setAgentId(src.getAgentId());
    }

    // private void copyUpdate(PropertyUpdateRequest src, Property dest) {
    // dest.setTitle(src.getTitle());
    // dest.setDescription(src.getDescription());
    // dest.setPrice(src.getPrice());
    // dest.setLocation(src.getLocation());
    // dest.setType(src.getType());
    // dest.setArea(src.getArea());
    // dest.setBedrooms(src.getBedrooms());
    // dest.setStatus(src.getStatus());
    // // dest.setAgentId(src.getAgentId());
    // }
}
