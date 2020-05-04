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

    public HouseTransactionServiceImpl(HouseDAO houseDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO, FinanceService financeService) {
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
        this.financeService = financeService;
    }

    @Override
    public ResponseEntity<Object> houseTransaction(TransactionDTO transactionDTO) {
        House house = houseDAO.findById(transactionDTO.getPropertyId());
        Finance finance = financeService.getFinance(transactionDTO.getCustomerId());
        if(finance.getAmount()>=house.getPrice()) { //jeśli kupujący ma więcej pieniędzy niż jest warta nieruchomość
            User customer = userDAO.findById(transactionDTO.getCustomerId());

            financeService.changeSellerFinance(house.getUser().id,house.getPrice());
            financeService.changeCustomerFinance(customer.getId(),house.getPrice());

            deassignSellerFromHouseInUserrealassets(transactionDTO.getPropertyId());
            assignNewOwnerToHouseInUserrealassets(house,customer);

            house = assignNewOwnerToHouse(house,customer);

            return new ResponseEntity<>(house, HttpStatus.OK);
        }else
            return new ResponseEntity<>("You have not enough money on account", HttpStatus.BAD_REQUEST);
    }


    private House assignNewOwnerToHouse(House house, User customer){
        house.setUser(customer);
        return houseDAO.save(house);
    }

    private UserRealAssets deassignSellerFromHouseInUserrealassets(int houseId){
        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(houseId);
        userRealAssets.setHouse(null);
        if(userRealAssets.getHouse()==null) //jeśli we wpisie w userrealassets był tylko dom - usuwamy cały wpis
            return userRealAssetsDAO.delete(userRealAssets);
        else
            return userRealAssetsDAO.save(userRealAssets);
    }

    private UserRealAssets assignNewOwnerToHouseInUserrealassets(House house, User customer){
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(customer);
        userRealAssets.setHouse(house);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
