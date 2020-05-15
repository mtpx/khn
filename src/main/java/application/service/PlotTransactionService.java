package application.service;

import application.dto.TransactionDTO;
import application.model.Plot;
import application.model.User;
import org.springframework.http.ResponseEntity;

public interface PlotTransactionService {
    ResponseEntity<Object> plotPreTransaction(TransactionDTO transactionDTO);
    Plot assignNewOwnerToPlot(Plot plot, User customer);
}
