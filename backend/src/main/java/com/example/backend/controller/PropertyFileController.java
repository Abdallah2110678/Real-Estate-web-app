package com.example.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.models.PropertyFile;
import com.example.backend.models.PropertyImage;
import com.example.backend.service.PropertyFileService;
import com.example.backend.service.PropertyImageService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*")
public class PropertyFileController {

  @Autowired
  private PropertyImageService propertyImageService;

  @Autowired
  private PropertyFileService propertyFileService;

  @PostMapping("/{id}/images")
  public ResponseEntity<PropertyImage> uploadImage(@PathVariable Integer id,
      @RequestParam("file") MultipartFile file) {
    try {
    
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      String filePath = "uploads/images/" + fileName;

    
      Files.write(Paths.get(filePath), file.getBytes());

      PropertyImage image = propertyImageService.saveImage(id, filePath);
      return ResponseEntity.ok(image);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  @PostMapping("/{id}/file")
  public ResponseEntity<PropertyFile> uploadFile(@PathVariable Integer id,
      @RequestParam("file") MultipartFile file) {
    try {
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      String filePath = "uploads/files/" + fileName;

      Files.write(Paths.get(filePath), file.getBytes());

      PropertyFile propertyFile = propertyFileService.saveFile(id, filePath);
      return ResponseEntity.ok(propertyFile);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}/file")
  public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
    List<PropertyFile> files = propertyFileService.getFilesByProperty(id);
    if (!files.isEmpty()) {
      try {
        Path filePath = Paths.get(files.get(0).getFilePath());
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"")
            .body(resource);
      } catch (Exception e) {
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.notFound().build();
  }
}