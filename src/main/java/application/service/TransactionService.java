package application.service;

import application.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<Object> executeTransaction(TransactionDTO transactionDTO);

}
