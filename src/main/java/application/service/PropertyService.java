package application.service;

import application.dto.PropertyDTO;
import org.springframework.http.ResponseEntity;

public interface PropertyService {
    ResponseEntity<Object> addProperty(PropertyDTO propertyDTO);
    ResponseEntity<Object> findAllProperties();
}
