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
    private final UserRealAssetsDAO userRealAssetsDAO;
    private final FinanceService financeService;

    public FlatTransactionServiceImpl(FlatDAO flatDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO, FinanceService financeService) {
        this.flatDAO = flatDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
        this.financeService = financeService;
    }

    @Override
    public ResponseEntity<Object> flatTransaction(TransactionDTO transactionDTO) {
        Flat flat = flatDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        if(finance.getAmount()>=flat.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż kosztuje mieszkanie
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(flat.getUser().id,flat.getPrice());
            financeService.changeCustomerFinance(customer.getId(),flat.getPrice());

            assignNewOwnerToFlatInUserrealassets(transactionDTO.getPropertyId(),customer);

            assignNewOwnerToFlat(flat,customer);

            return new ResponseEntity<>("You bought house (id: "+flat.getId()+", area:  "+flat.getSize()+", price: "+flat.getPrice()+")", HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }

    private Flat assignNewOwnerToFlat(Flat flat, User customer){
        flat.setUser(customer);
        return flatDAO.save(flat);
    }

    private UserRealAssets assignNewOwnerToFlatInUserrealassets(int flatId, User customer){
        UserRealAssets userRealAssets = userRealAssetsDAO.getByFlatId(flatId);
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
