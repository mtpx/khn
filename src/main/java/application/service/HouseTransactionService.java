package application.service;

import application.dto.TransactionDTO;
import application.model.Finance;
import application.model.House;
import application.model.Plot;
import org.springframework.http.ResponseEntity;

public interface HouseTransactionService {
    ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO);
    ResponseEntity<Object> houseAndPlotTransaction(Finance finance, House house, Plot plot, TransactionDTO transactionDTO);
}
