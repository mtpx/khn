package application.service;

import application.dto.FlatDTO;
import org.springframework.http.ResponseEntity;

public interface AddFlatService {
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
}
