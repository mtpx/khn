package application.service;

import application.dao.*;
import application.dto.TransactionDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlotTransactionServiceImpl implements PlotTransactionService {
    final static Logger LOGGER = Logger.getLogger(PlotTransactionServiceImpl.class.getName());

    private final PlotDAO plotDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;
    private final FinanceService financeService;
    private final HouseTransactionService houseTransactionService;

    public PlotTransactionServiceImpl(PlotDAO plotDAO, UserDAO userDAO, UserRealAssetsService userRealAssetsService, FinanceService financeService, HouseTransactionService houseTransactionService) {
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
        this.financeService = financeService;
        this.houseTransactionService = houseTransactionService;
    }

    @Override
    public Plot assignNewOwnerToPlot(Plot plot, User customer){
        plot.setUser(customer);
        plot.setSold(true);
        return plotDAO.save(plot);
    }

    @Override
    public ResponseEntity<Object> plotPreTransaction(TransactionDTO transactionDTO) {
        Plot plot = plotDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        UserRealAssets userRealAssets = userRealAssetsService.getByPlotId(transactionDTO.getPropertyId());
        if(userRealAssets.getHouse()==null) //jeśli kupujemy samą działkę (bez domu)
            return verifyFinance(finance,plot,transactionDTO);
        else    //jeśli kupujemy działkę z domem
            return houseTransactionService.houseAndPlotVerifyFinance(finance,userRealAssets.getHouse(),plot,transactionDTO);
    }

    private ResponseEntity<Object> verifyFinance(Finance finance, Plot plot, TransactionDTO transactionDTO) {
        if(finance.getAmount()>=plot.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            return plotOnlyTransaction(plot, transactionDTO);
        }else //jeśli kupujący ma mniej pieniędzy niż jest warta nieruchomość
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> plotOnlyTransaction(Plot plot, TransactionDTO transactionDTO){
        User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

        financeService.changeFinanceAfterTransaction(customer.getId(),plot.getUser().id,plot.getPrice()); //zmieniamy stany kont dla sprzedającego i kupującego
        userRealAssetsService.assignNewOwnerToPlot(plot,customer); //podmieniamy właściciela w userrealassets
        plot = assignNewOwnerToPlot(plot,customer); //podmieniamy własciciela działki w tabeli plot
        return new ResponseEntity<>("You bought plot( id: "+plot.getId()+", area:  "+plot.getSize()+", price: "+plot.getPrice()+")", HttpStatus.OK);
    }
}
