package application.service;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Address;
import application.model.views.AuctionView;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AuctionViewService {
    List<AuctionView> findAllProperties();
    ResponseEntity<Object> findPropertiesByType(String propertyType);


}
