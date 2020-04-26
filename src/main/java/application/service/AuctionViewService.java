package application.service;

import org.springframework.http.ResponseEntity;

public interface AuctionViewService {
    ResponseEntity<Object> findAllProperties();
    ResponseEntity<Object> findPropertiesByType(String propertyType);
}
