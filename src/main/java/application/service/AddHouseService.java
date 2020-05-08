package application.service;

import application.dto.HouseDTO;
import org.springframework.http.ResponseEntity;

public interface AddHouseService {
    ResponseEntity<Object> addHouse(HouseDTO houseDTO);
}
