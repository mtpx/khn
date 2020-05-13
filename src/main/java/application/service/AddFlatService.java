package application.service;

import application.dto.FlatDTO;
import org.springframework.http.ResponseEntity;

// Nazwa serwisu - add jest jedną z metod serwisu więc tak się on nie powinien nazywać :) [Komentarz dotyczy również pozostałych serwisów i addFacade]
public interface AddFlatService {
    ResponseEntity<Object> addFlat(FlatDTO flatDTO);
}
