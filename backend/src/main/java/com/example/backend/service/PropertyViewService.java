package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.models.Property;
import com.example.backend.models.PropertyView;
import com.example.backend.repository.PropertyRepository;
import com.example.backend.repository.PropertyViewRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class PropertyViewService {
    
    @Autowired
    private PropertyViewRepository propertyViewRepository;
    
    public void recordView(Integer propertyId, String ipAddress) {
        PropertyView view = new PropertyView();
        view.setPropertyId(propertyId);
        view.setIpAddress(ipAddress);
        propertyViewRepository.save(view);
    }
    
    public Long getViewCount(Integer propertyId) {
        return propertyViewRepository.countByPropertyId(propertyId);
    }
}