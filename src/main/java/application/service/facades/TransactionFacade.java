package application.service.facades;

import application.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface TransactionFacade {
    ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO);
    ResponseEntity<Object> flatTransaction(TransactionDTO transactionDTO);
    ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO);
}
