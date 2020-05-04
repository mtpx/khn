package application.service.facades;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;

public interface AddPropertyFacade {
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);
}
