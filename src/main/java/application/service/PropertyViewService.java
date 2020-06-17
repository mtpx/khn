package application.service;

import application.model.views.PropertyView;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PropertyViewService {
    List<PropertyView> findAllForSale();
    ResponseEntity<Object> findByTypeForSale(String propertyType);
    List<PropertyView> findByEmailForSale(String email);

    List<PropertyView> findByEmailSold(String email);

}
