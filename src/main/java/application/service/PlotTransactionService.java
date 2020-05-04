package application.service;

import application.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface PlotTransactionService {
    ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO);
}
