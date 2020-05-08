package application.service;

import application.dao.*;
import application.model.views.AuctionView;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuctionViewServiceImpl implements AuctionViewService {

    final static Logger LOGGER = Logger.getLogger(AuctionViewServiceImpl.class.getName());
    private final AuctionViewDAO auctionViewDAO;

    public AuctionViewServiceImpl(AuctionViewDAO auctionViewDAO) {
        this.auctionViewDAO = auctionViewDAO;
    }

    @Override
    public List<AuctionView> findAllProperties() {
        return auctionViewDAO.findAll();
    }

    @Override
    public ResponseEntity<Object> findPropertiesByType(String propertyType) {
        return new ResponseEntity<>(auctionViewDAO.findByType(propertyType), HttpStatus.OK);
    }
}
