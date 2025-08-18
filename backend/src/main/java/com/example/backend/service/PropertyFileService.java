package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.model.PropertyFile;
import com.example.backend.repository.PropertyFileRepository;
import java.util.List;

@Service
@Transactional
public class PropertyFileService {
    
    @Autowired
    private PropertyFileRepository propertyFileRepository;
    
    public List<PropertyFile> getFilesByProperty(Integer propertyId) {
        return propertyFileRepository.findByPropertyId(propertyId);
    }
    
    public PropertyFile saveFile(Integer propertyId, String filePath) {
        PropertyFile file = new PropertyFile();
        file.setPropertyId(propertyId);
        file.setFilePath(filePath);
        return propertyFileRepository.save(file);
    }
}