package application.service;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;

public interface PropertyService {
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
}
