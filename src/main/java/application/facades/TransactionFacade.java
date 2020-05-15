package application.facades;

import application.dto.TransactionDTO;
import application.service.FlatTransactionService;
import application.service.HouseTransactionService;
import application.service.PlotTransactionService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacade {

    final static Logger LOGGER = Logger.getLogger(TransactionFacade.class.getName());
    private final PlotTransactionService plotTransactionService;
    private final FlatTransactionService flatTransactionService;
    private final HouseTransactionService houseTransactionService;

    public TransactionFacade(PlotTransactionService plotTransactionService, FlatTransactionService flatTransactionService,HouseTransactionService houseTransactionService) {
        this.plotTransactionService = plotTransactionService;
        this.flatTransactionService = flatTransactionService;
        this.houseTransactionService = houseTransactionService;
    }

    public ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO) {
       return plotTransactionService.plotPreTransaction(transactionDTO);
    }

    public ResponseEntity<Object> flatTransaction(TransactionDTO transactionDTO) {
        return flatTransactionService.verifyFinance(transactionDTO);
    }

    public ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO) {
        return houseTransactionService.verifyExistingProperty(transactionDTO);
    }
}
