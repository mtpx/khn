package application.service;

import application.dto.*;
import org.springframework.http.ResponseEntity;

public interface PropertyService {
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);

    ResponseEntity<Object> findAllProperties();
    ResponseEntity<Object> findPropertiesByType(String propertyType);

}
