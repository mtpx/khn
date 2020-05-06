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
    private final UserRealAssetsDAO userRealAssetsDAO;
    private final FinanceService financeService;
    private final HouseTransactionService houseTransactionService;

    public PlotTransactionServiceImpl(PlotDAO plotDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO, FinanceService financeService, HouseTransactionService houseTransactionService) {
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
        this.financeService = financeService;
        this.houseTransactionService = houseTransactionService;
    }

    @Override
    public ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO) {
        Plot plot = plotDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        UserRealAssets userRealAssets = userRealAssetsDAO.getByPlotId(transactionDTO.getPropertyId());
        if(userRealAssets.getHouse()==null) //jeśli kupujemy samą działkę (bez domu)
            return plotOnlyTransaction(finance,plot,transactionDTO);
        else    //jeśli kupujemy działkę z domem
            return houseTransactionService.houseAndPlotTransaction(finance,userRealAssets.getHouse(),plot,transactionDTO);
    }

    private ResponseEntity<Object> plotOnlyTransaction(Finance finance, Plot plot, TransactionDTO transactionDTO) {

        if(finance.getAmount()>=plot.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(plot.getUser().id,plot.getPrice());
            financeService.changeCustomerFinance(customer.getId(),plot.getPrice());

            assignNewOwnerToPlotInUserrealassets(plot,customer);

            plot = assignNewOwnerToPlot(plot,customer);
            return new ResponseEntity<>("You bought plot( id: "+plot.getId()+", area:  "+plot.getSize()+", price: "+plot.getPrice()+")", HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Plot assignNewOwnerToPlot(Plot plot, User customer){
        plot.setUser(customer);
        return plotDAO.save(plot);
    }

    private UserRealAssets assignNewOwnerToPlotInUserrealassets(Plot plot, User customer){
        UserRealAssets userRealAssets = userRealAssetsDAO.getByPlotId(plot.getId());
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
