package application.service;

import application.dto.HouseDTO;
import org.springframework.http.ResponseEntity;

public interface HouseService {
    ResponseEntity<Object> verifyAddress(HouseDTO houseDTO);
}
