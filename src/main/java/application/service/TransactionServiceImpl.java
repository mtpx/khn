package application.service;

import application.dao.*;
import application.dto.TransactionDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    final static Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class.getName());

    private final FlatDAO flatDAO;
    private final PlotDAO plotDAO;
    private final HouseDAO houseDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;



    public TransactionServiceImpl(FlatDAO flatDAO, PlotDAO plotDAO, HouseDAO houseDAO, UserDAO userDAO, UserRealAssetsDAO userRealAssetsDAO) {
        this.flatDAO = flatDAO;
        this.plotDAO = plotDAO;
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;

    }


    @Override
    public ResponseEntity<Object> executeTransaction(TransactionDTO transactionDTO) {
        User user = userDAO.findById(transactionDTO.getCustomerId());
        switch (transactionDTO.getPropertyType()) {
            case "flat":
                Flat flat = flatDAO.findById(transactionDTO.getPropertyId());
                flat.setUser(user);
                flatDAO.save(flat);
                UserRealAssets userRealAssets = userRealAssetsDAO.getByFlatId(transactionDTO.getPropertyId());
                userRealAssets.setUser(user);
                userRealAssetsDAO.save(userRealAssets);
                return new ResponseEntity<>(flat, HttpStatus.OK);
            case "house":
                House house = houseDAO.findById(transactionDTO.getPropertyId()); break;
            case "plot":
                Plot plot = plotDAO.findById(transactionDTO.getPropertyId()); break;
        }
        return new ResponseEntity<>("invalid property type", HttpStatus.BAD_REQUEST);
    }
}
