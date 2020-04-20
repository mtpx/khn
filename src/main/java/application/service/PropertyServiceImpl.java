package application.service;

import application.dao.*;
import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.dto.PropertyBaseDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PropertyServiceImpl implements PropertyService {
    final static Logger LOGGER = Logger.getLogger(PropertyServiceImpl.class.getName());

    private final AddressDAO addressDAO;
    private final AuctionViewDAO auctionViewDAO;
    private final FlatDAO flatDAO;
    private final HouseDAO houseDAO;
    private final PlotDAO plotDAO;
    private final UserDAO userDAO;


    public PropertyServiceImpl(AddressDAO addressDAO, AuctionViewDAO auctionViewDAO, FlatDAO flatDAO, HouseDAO houseDAO, PlotDAO plotDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.auctionViewDAO = auctionViewDAO;
        this.flatDAO = flatDAO;
        this.houseDAO = houseDAO;
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;

    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        Address address = new Address();
        address.setCity(flatDTO.getCity());
        address.setHomeNumber(flatDTO.getHouseNumber());
        address.setLocalNumber(flatDTO.getLocalNumber());
        address.setPostCode(flatDTO.getPostCode());
        address.setStreet(flatDTO.getStreet());
        address.setRealAssets(new RealAssets(1,"flat"));
        if(addressDAO.verifyAddress(address).size()!=0) //jeśli w bazie istnieje podany adres nie możemy dodać mieszkania
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        else {
            User user = userDAO.findById(flatDTO.getUserId());
            Flat flat = new Flat();
            flat.setFloor(flatDTO.getFloor());
            flat.setAddress(address);
            flat.setUser(user);
            flat.setSize(flatDTO.getSize());
            flat.setPrice(flatDTO.getPrice());
            flat.setRooms(flatDTO.getRooms());
            return new ResponseEntity<>(flatDAO.save(flat), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        Address address = new Address();
        address.setCity(houseDTO.getCity());
        address.setHomeNumber(houseDTO.getHouseNumber());
        address.setLocalNumber(houseDTO.getLocalNumber());
        address.setPostCode(houseDTO.getPostCode());
        address.setStreet(houseDTO.getStreet());
        address.setRealAssets(new RealAssets(2, "house"));

        User user = userDAO.findById(houseDTO.getUserId());
        House house = new House();

        List<Address> existingAddresses = addressDAO.verifyAddress(address);
        if (existingAddresses.size() > 0) {
            if (existingAddresses.get(0).getRealAssets().getType().equals("house") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))
                return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        }
        house.setAddress(address);
        house.setUser(user);
        house.setRooms(houseDTO.getRooms());
        house.setSize(houseDTO.getSize());
        house.setPrice(houseDTO.getPrice());
        return new ResponseEntity<>(houseDAO.save(house), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        Address address = new Address();
        address.setCity(plotDTO.getCity());
        address.setHomeNumber(plotDTO.getHouseNumber());
        address.setLocalNumber(plotDTO.getLocalNumber());
        address.setPostCode(plotDTO.getPostCode());
        address.setStreet(plotDTO.getStreet());
        address.setRealAssets(new RealAssets(3,"plot"));
        User user = userDAO.findById(plotDTO.getUserId());
        Plot plot = new Plot();
        plot.setAddress(address);


        List<Address> existingAddresses = addressDAO.verifyAddress(address);
        if (existingAddresses.size() > 0) {
            if (existingAddresses.get(0).getRealAssets().getType().equals("plot") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))
                return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
            if (existingAddresses.get(0).getRealAssets().getType().equals("house")) {
                plot.setHouse(houseDAO.findByAddressId(existingAddresses.get(0).getId()));
                plot.setAddress(addressDAO.findById(existingAddresses.get(0).getId()));
            }
        }

        plot.setUser(user);
        plot.setSize(plotDTO.getSize());
        plot.setPrice(plotDTO.getPrice());
        plot.setType(plotDTO.getType());
        return new ResponseEntity<>(plotDAO.save(plot), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> findAllProperties() {
        return new ResponseEntity<>(auctionViewDAO.findAll(), HttpStatus.OK);
    }


//    @Override
//    public ResponseEntity<Object> addProperty(PropertyBaseDTO propertyBaseDTO) {
//        Address address = new Address();
//        address.setCity(propertyBaseDTO.getCity());
//        address.setHomeNumber(propertyBaseDTO.getHouseNumber());
//        address.setLocalNumber(propertyBaseDTO.getHouseNumber());
//        address.setPostCode(propertyBaseDTO.getPostCode());
//        address.setStreet(propertyBaseDTO.getStreet());
//
//        User user = userDAO.findById(propertyBaseDTO.getUserId());
//        switch (propertyBaseDTO.getRealAssetId()){
//            case 1: //house
//                House house = new House();
//                house.setPrice(propertyBaseDTO.getPrice());
//                house.setRooms(propertyBaseDTO.getRooms());
//                house.setSize(propertyBaseDTO.getSize());
//                house.setUser(user);
//                house.setAddress(address);
//                return new ResponseEntity<>(houseDAO.save(house), HttpStatus.CREATED);
//            case 2: //flat
//
//            case 3: //plot
//                Plot plot = new Plot();
//                plot.setAddress(address);
//                plot.setUser(user);
//                plot.setSize(propertyBaseDTO.getSize());
//                plot.setPrice(propertyBaseDTO.getSize());
//                plot.setType(propertyBaseDTO.getType());
//                return new ResponseEntity<>(plotDAO.save(plot), HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("invalid asset id", HttpStatus.CREATED);
//    }
}
