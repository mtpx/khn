package application.service.facades;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;
// folder z Facade można wynieść wyżej
// Taka ogólna uwaga do Facade - nie trzeba tworzyć interfejsu oraz jego implementacji
// Wystarczy PropertyFacade i tam w klasie sobie działać ( w skrócie wywalić interfejsy :))
public interface AddPropertyFacade {
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);
}
