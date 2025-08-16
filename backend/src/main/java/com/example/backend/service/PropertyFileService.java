package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.models.Property;
import com.example.backend.models.PropertyFile;
import com.example.backend.repository.PropertyFileRepository;
import com.example.backend.repository.PropertyRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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