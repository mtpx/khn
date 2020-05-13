package application.service;

import application.dao.*;
import application.dto.TransactionDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlatTransactionServiceImpl implements FlatTransactionService {
    final static Logger LOGGER = Logger.getLogger(FlatTransactionServiceImpl.class.getName());

    private final FlatDAO flatDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;
    private final FinanceService financeService;

    public FlatTransactionServiceImpl(FlatDAO flatDAO, UserDAO userDAO, UserRealAssetsService userRealAssetsService, FinanceService financeService) {
        this.flatDAO = flatDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
        this.financeService = financeService;
    }

    @Override
    // W tej metodzie pomyślałabym o uproszczeniu i podzieleniu na mniejsze
    public ResponseEntity<Object> flatTransaction(TransactionDTO transactionDTO) {
        Flat flat = flatDAO.findById(transactionDTO.getPropertyId()); //pobieramy sprzedawane mieszkanie
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());    //pobieramy finanse kupującego
        if(finance.getAmount()>=flat.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż kosztuje mieszkanie
            User customer = userDAO.findById(transactionDTO.getCustomerId()); //pobieramy kupującego

            financeService.changeSellerFinance(flat.getUser().id,flat.getPrice()); //zmieniamy stany kont
            financeService.changeCustomerFinance(customer.getId(),flat.getPrice());

            userRealAssetsService.assignNewOwnerToFlatInUserRealAssets(flatDAO.findById(transactionDTO.getPropertyId()),customer); //podmieniamy właściciela w userrealassets

            assignNewOwnerToFlat(flat,customer); //podmieniamy właściciela w tabeli flat
            return new ResponseEntity<>("You bought flat (id: "+flat.getId()+", area:  "+flat.getSize()+", price: "+flat.getPrice()+")", HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private Flat assignNewOwnerToFlat(Flat flat, User customer){
        flat.setUser(customer);
        return flatDAO.save(flat);
    }
}
