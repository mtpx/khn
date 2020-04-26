package application.service;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;

public interface PropertyFacadeService {
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);

}
