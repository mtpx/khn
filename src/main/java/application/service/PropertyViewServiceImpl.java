package application.service;

import application.dao.*;
import application.model.views.PropertyView;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("propertyViewService")
public class PropertyViewServiceImpl implements PropertyViewService {

    final static Logger LOGGER = Logger.getLogger(PropertyViewServiceImpl.class.getName());
    private final PropertyViewDAO propertyViewDAO;

    public PropertyViewServiceImpl(PropertyViewDAO propertyViewDAO) {
        this.propertyViewDAO = propertyViewDAO;
    }

    @Override
    public List<PropertyView> findAllForSale() {
        return propertyViewDAO.findAllForSale();
    }

    @Override
    public ResponseEntity<Object> findByTypeForSale(String propertyType) {
        return new ResponseEntity<>(propertyViewDAO.findByTypeForSale(propertyType), HttpStatus.OK);
    }

    @Override
    public List<PropertyView> findByEmailForSale(String email) {
        return propertyViewDAO.findByEmailForSale(email);
    }

    @Override
    public List<PropertyView> findByEmailSold(String email) {
        return  propertyViewDAO.findByEmailSold(email);
    }
}
