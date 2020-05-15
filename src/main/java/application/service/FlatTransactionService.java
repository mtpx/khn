package application.service;

import application.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface FlatTransactionService {
    ResponseEntity<Object> verifyFinance(TransactionDTO transactionDTO);

}
