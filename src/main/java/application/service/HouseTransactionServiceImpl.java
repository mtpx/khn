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
    public ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO) {
        House house = houseDAO.findById(transactionDTO.getPropertyId()); //pobieramy sprzedawany dom
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId()); //pobieramy finanse kupującego
        UserRealAssets userRealAssets = userRealAssetsService.getByHouseId(transactionDTO.getPropertyId()); //pobieramy wpis z userrealassets zawierający sprzedawany dom
        if(userRealAssets.getPlot()==null) //jeśli wpis z domem w userrealassets w polu plot_id ma null kupujemy sam dom (bez działki)
            return houseOnlyTransaction(finance,house,transactionDTO);
        else    //jeśli plot nie jest nullem - kupujemy dom z działką
            return houseAndPlotTransaction(finance,house,userRealAssets.getPlot(),transactionDTO);
    }


    // Prywatne metody powinny być pod public :) - to taka ogólna uwaga
    private House assignNewOwnerToHouse(House house, User customer){
        house.setUser(customer);
        return houseDAO.save(house);
    }

    @Override
    // metoda do podzielenia na mniejsze :)
    public ResponseEntity<Object> houseAndPlotTransaction(Finance finance, House house, Plot plot, TransactionDTO transactionDTO){
        int houseAndPlotPrice = house.getPrice()+plot.getPrice(); // sumujemy wartość domu i działki
        if(finance.getAmount()>=houseAndPlotPrice) { //jeśli kupujący ma więcej pieniędzy niż cena domu + działki
            User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

            // oddzielne metody do finansów? [wtedy i houseOnlyTransaction skorzysta]
            financeService.changeSellerFinance(house.getUser().id,houseAndPlotPrice); //zmieniamy stany kont
            financeService.changeCustomerFinance(customer.getId(),houseAndPlotPrice);

            userRealAssetsService.assignNewOwnerToHouseInUserRealAssets(house,customer); //podmieniamy właściciela w userrealassets

            // kolejna metoda, ktora mozna wyniesc do prywatnej
            assignNewOwnerToPlot(plot,customer); //podmieniamy właściela w tabelach plot i house
            assignNewOwnerToHouse(house,customer);

            return new ResponseEntity<>("You bought house (id: "+house.getId()+", area: "+house.getSize()+", price: "+house.getPrice()+
                    ") with plot (id: "+plot.getId()+", area "+plot.getSize()+", price: "+plot.getPrice()+") . Total price: "+houseAndPlotPrice , HttpStatus.OK);
        }else //jeśli kupujący ma mniej pieniędzy niż cena domu + działki
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> houseOnlyTransaction(Finance finance, House house, TransactionDTO transactionDTO){
        if(finance.getAmount()>=house.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

            financeService.changeSellerFinance(house.getUser().id,house.getPrice()); //zmieniamy finanse
            financeService.changeCustomerFinance(customer.getId(),house.getPrice());

            userRealAssetsService.assignNewOwnerToHouseInUserRealAssets(house,customer); //podmieniamy właściciela nieruchomosci w userrealassets
            assignNewOwnerToHouse(house,customer);  //podmieniamy właściciela domu w tabeli house
            return new ResponseEntity<>("You bought house (id: "+house.getId()+", area:  "+house.getSize()+", price: "+house.getPrice()+")", HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private Plot assignNewOwnerToPlot(Plot plot, User customer) {
        plot.setUser(customer);
        return plotDAO.save(plot);
    }
}
