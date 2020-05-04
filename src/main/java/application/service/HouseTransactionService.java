package application.service;

import application.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface HouseTransactionService {
    ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO);

}
