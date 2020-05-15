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
    private final UserRealAssetsService userRealAssetsService;
    private final FinanceService financeService;
    private final PlotDAO plotDAO;

    public HouseTransactionServiceImpl(HouseDAO houseDAO, UserDAO userDAO, UserRealAssetsService userRealAssetsService, FinanceService financeService, PlotDAO plotDAO) {
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
        this.financeService = financeService;
        this.plotDAO = plotDAO;
    }

    @Override
    public ResponseEntity<Object> verifyExistingProperty(TransactionDTO transactionDTO) {
        House house = houseDAO.findById(transactionDTO.getPropertyId()); //pobieramy sprzedawany dom
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId()); //pobieramy finanse kupującego
        UserRealAssets userRealAssets = userRealAssetsService.getByHouseId(transactionDTO.getPropertyId()); //pobieramy wpis z userrealassets zawierający sprzedawany dom

        if(userRealAssets.getPlot()==null) //jeśli wpis z domem w userrealassets w polu plot_id ma null kupujemy sam dom (bez działki)
            return houseTransactionVerifyFinance(finance,house,transactionDTO);
        else    //jeśli plot nie jest nullem - kupujemy dom z działką
            return houseAndPlotVerifyFinance(finance,house,userRealAssets.getPlot(),transactionDTO);
    }

    @Override
    public ResponseEntity<Object> houseAndPlotVerifyFinance(Finance finance, House house, Plot plot, TransactionDTO transactionDTO){
        int houseAndPlotPrice = house.getPrice()+plot.getPrice(); // sumujemy wartość domu i działki

        if(finance.getAmount()>=houseAndPlotPrice) { //jeśli kupujący ma więcej pieniędzy niż cena domu + działki
            return houseAndPlotTransaction(transactionDTO,house,plot,houseAndPlotPrice);
        }else //jeśli kupujący ma mniej pieniędzy niż cena domu + działki
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> houseTransactionVerifyFinance(Finance finance, House house, TransactionDTO transactionDTO){
        if(finance.getAmount()>=house.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            return houseTransaction(transactionDTO,house);
        }else //jeśli kupującego nie stać na nieruchomość
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> houseAndPlotTransaction(TransactionDTO transactionDTO, House house, Plot plot, int houseAndPlotPrice){
        User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

        financeService.changeFinanceAfterTransaction(customer.getId(),house.getUser().id,houseAndPlotPrice); //zmieniamy stany kont
        assignNewOwnerToHouseAndPlot(plot,house,customer);//podmieniamy właściela w tabelach plot/house/userrealassets
        return new ResponseEntity<>("You bought house (id: "+house.getId()+", area: "+house.getSize()+", price: "+house.getPrice()+
                ") with plot (id: "+plot.getId()+", area "+plot.getSize()+", price: "+plot.getPrice()+") . Total price: "+houseAndPlotPrice , HttpStatus.OK);
    }

    private ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO, House house){
        User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

        financeService.changeFinanceAfterTransaction(customer.getId(),house.getUser().id,house.getPrice());
        setNewHouseOwner(house,customer); //zmiana właściciela domu w tabelach userrealassets/house
        return new ResponseEntity<>("You bought house (id: "+house.getId()+", area:  "+house.getSize()+", price: "+house.getPrice()+")", HttpStatus.OK);
    }

    private void setNewHouseOwner(House house, User customer) {
        userRealAssetsService.assignNewOwnerToHouse(house,customer); //podmieniamy właściciela nieruchomosci w userrealassets
        assignNewOwnerToHouse(house,customer);  //podmieniamy właściciela domu w tabeli house
    }

    private void assignNewOwnerToPlot(Plot plot, User customer) {
        plot.setUser(customer);
        plotDAO.save(plot);
    }

    private void assignNewOwnerToHouse(House house, User customer){
        house.setUser(customer);
        houseDAO.save(house);
    }

    private void assignNewOwnerToHouseAndPlot(Plot plot, House house, User customer){
        assignNewOwnerToPlot(plot,customer);  //podmieniamy własciela w tabeli plot
        assignNewOwnerToHouse(house,customer);  //w tabeli house
        userRealAssetsService.assignNewOwnerToHouse(house,customer); //w userrealassets
    }
}
