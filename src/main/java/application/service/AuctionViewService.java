package application.service;

import application.model.views.AuctionView;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AuctionViewService {
    List<AuctionView> findAllProperties();
    ResponseEntity<Object> findPropertiesByType(String propertyType);
}
