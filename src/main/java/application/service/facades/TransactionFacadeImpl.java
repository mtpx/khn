package application.service.facades;

import application.dto.TransactionDTO;
import application.service.FlatTransactionService;
import application.service.HouseTransactionService;
import application.service.PlotTransactionService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacadeImpl implements TransactionFacade {

    final static Logger LOGGER = Logger.getLogger(TransactionFacadeImpl.class.getName());
    private final PlotTransactionService plotTransactionService;
    private final FlatTransactionService flatTransactionService;
    private final HouseTransactionService houseTransactionService;

    public TransactionFacadeImpl(PlotTransactionService plotTransactionService, FlatTransactionService flatTransactionService,HouseTransactionService houseTransactionService) {
        this.plotTransactionService = plotTransactionService;
        this.flatTransactionService = flatTransactionService;
        this.houseTransactionService = houseTransactionService;
    }

    @Override
    public ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO) {
       return plotTransactionService.plotTransaction(transactionDTO);
    }

    @Override
    public ResponseEntity<Object> flatTransaction(TransactionDTO transactionDTO) {
        return flatTransactionService.flatTransaction(transactionDTO);
    }

    @Override
    public ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO) {
        return houseTransactionService.houseTransaction(transactionDTO);
    }
}
