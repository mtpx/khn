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

    public PlotTransactionServiceImpl(PlotDAO plotDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO, FinanceService financeService) {
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
        this.financeService = financeService;
    }

    @Override
    public ResponseEntity<Object> plotTransaction(TransactionDTO transactionDTO) {
        Plot plot = plotDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        if(finance.getAmount()>=plot.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(plot.getUser().id,plot.getPrice());
            financeService.changeCustomerFinance(customer.getId(),plot.getPrice());

            deassignSellerFromPlotInUserrealassets(transactionDTO.getPropertyId());
            assignNewOwnerToPlotInUserrealassets(plot,customer);

            plot = assignNewOwnerToPlot(plot,customer);

            return new ResponseEntity<>(plot, HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }


    private Plot assignNewOwnerToPlot(Plot plot, User customer){
        plot.setUser(customer);
        return plotDAO.save(plot);
    }

    private UserRealAssets deassignSellerFromPlotInUserrealassets(int plotId){
        UserRealAssets userRealAssets = userRealAssetsDAO.getByPlotId(plotId);
        userRealAssets.setPlot(null);
        if(userRealAssets.getHouse()==null) //jeśli we wpisie w userrealassets była tylko działka - usuwamy cały wpis
            return userRealAssetsDAO.delete(userRealAssets);
        else    //jeśli działka+dom -> plotId=null + zapis wpisu z samym domem
            return userRealAssetsDAO.save(userRealAssets);
    }

    private UserRealAssets assignNewOwnerToPlotInUserrealassets(Plot plot, User customer){
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(customer);
        userRealAssets.setPlot(plot);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
