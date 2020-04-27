package application.service;

import application.dto.TransactionDTO;
import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropertyFacadeServiceImpl implements PropertyFacadeService {
    final static Logger LOGGER = Logger.getLogger(PropertyFacadeServiceImpl.class.getName());

    private final FlatService flatService;
    private final HouseService houseService;
    private final PlotService plotService;
    private final TransactionService transactionService;


    public PropertyFacadeServiceImpl(FlatService flatService, HouseService houseService, PlotService plotService,TransactionService transactionService) {
        this.flatService = flatService;
        this.houseService = houseService;
        this.plotService = plotService;
        this.transactionService = transactionService;
    }


    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        return plotService.addPlot(plotDTO);
    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        return flatService.addFlat(flatDTO);
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        return houseService.addHouse(houseDTO);
    }

    @Override
    public ResponseEntity<Object> executeTransaction(TransactionDTO transactionDTO) {
       return transactionService.executeTransaction(transactionDTO);
    }
}
