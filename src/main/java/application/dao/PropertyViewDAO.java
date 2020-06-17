package application.dao;

import application.model.views.PropertyView;
import java.util.List;

public interface PropertyViewDAO {
    List<PropertyView> findAllForSale();
    List<PropertyView> findByTypeForSale(String propertyType);
    List<PropertyView> findByEmailForSale(String email);
    List<PropertyView> findByEmailSold(String email);
}
