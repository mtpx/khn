package application.service;

import application.dto.FlatDTO;
import org.springframework.http.ResponseEntity;

public interface FlatService {
    ResponseEntity<Object> verifyAddress(FlatDTO flatDTO);
}
