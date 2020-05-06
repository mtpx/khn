package application.service;

import application.dao.*;
import application.dto.TransactionDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HouseTransactionServiceImpl implements HouseTransactionService {
    final static Logger LOGGER = Logger.getLogger(HouseTransactionServiceImpl.class.getName());
    private final HouseDAO houseDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;
    private final FinanceService financeService;
    private final PlotDAO plotDAO;

    public HouseTransactionServiceImpl(HouseDAO houseDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO, FinanceService financeService, PlotDAO plotDAO) {
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
        this.financeService = financeService;
        this.plotDAO = plotDAO;
    }

    @Override
    public ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO) {
        House house = houseDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(transactionDTO.getPropertyId());
        if(userRealAssets.getPlot()==null) //jeśli kupujemy sam dom (bez działki)
            return houseOnlyTransaction(finance,house,transactionDTO);
        else    //jeśli kupujemy dom z działką
            return houseAndPlotTransaction(finance,house,userRealAssets.getPlot(),transactionDTO);
    }

    private House assignNewOwnerToHouse(House house, User customer){
        house.setUser(customer);
        return houseDAO.save(house);
    }

    private UserRealAssets assignNewOwnerToHouseInUserrealassets(House house, User customer){
        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(house.getId());
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }

    private ResponseEntity<Object> houseOnlyTransaction(Finance finance, House house, TransactionDTO transactionDTO){
        if(finance.getAmount()>=house.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(house.getUser().id,house.getPrice());
            financeService.changeCustomerFinance(customer.getId(),house.getPrice());

            assignNewOwnerToHouseInUserrealassets(house,customer);
            assignNewOwnerToHouse(house,customer);
            return new ResponseEntity<>("You bought house (id: "+house.getId()+", area:  "+house.getSize()+", price: "+house.getPrice()+")", HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> houseAndPlotTransaction(Finance finance, House house, Plot plot, TransactionDTO transactionDTO){
        int houseAndPlotPrice = house.getPrice()+plot.getPrice();
        if(finance.getAmount()>=houseAndPlotPrice) { //jeśli kupujący ma więcej pieniędzy niż cena domu + działki
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(house.getUser().id,houseAndPlotPrice);
            financeService.changeCustomerFinance(customer.getId(),houseAndPlotPrice);

            assignNewOwnerToHouseInUserrealassets(house,customer);

            assignNewOwnerToPlot(plot,customer);
            assignNewOwnerToHouse(house,customer);

            return new ResponseEntity<>("You bought house (id: "+house.getId()+", area: "+house.getSize()+", price: "+house.getPrice()+
                    ") with plot (id: "+plot.getId()+", area "+plot.getSize()+", price: "+plot.getPrice()+") . Total price: "+houseAndPlotPrice , HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private Plot assignNewOwnerToPlot(Plot plot, User customer) {
        plot.setUser(customer);
        return plotDAO.save(plot);
    }
}
