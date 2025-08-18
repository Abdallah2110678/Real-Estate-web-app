package com.example.backend.service;

import com.example.backend.model.PropertyImage;
import com.example.backend.repository.PropertyImageRepository;
// import com.example.backend.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.List;
// import java.util.UUID;
@Service
@Transactional
public class PropertyImageService {
    
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    
    public List<PropertyImage> getImagesByProperty(Integer propertyId) {
        return propertyImageRepository.findByPropertyId(propertyId);
    }
    
    public PropertyImage saveImage(Integer propertyId, String filePath) {
        PropertyImage image = new PropertyImage();
        image.setPropertyId(propertyId);
        image.setFilePath(filePath);
        return propertyImageRepository.save(image);
    }
}