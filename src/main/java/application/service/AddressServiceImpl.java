package application.service;

import application.dao.AddressDAO;
import application.dao.FlatDAO;
import application.dao.UserDAO;
import application.dao.UserRealAssetsDAO;
import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.*;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    final static Logger LOGGER = Logger.getLogger(AddressServiceImpl.class.getName());
    private final AddressDAO addressDAO;

    public AddressServiceImpl(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public Address createAddressObject(FlatDTO flatDTO) {
        Address address = new Address();
        address.setCity(flatDTO.getCity());
        address.setHomeNumber(flatDTO.getHouseNumber());
        address.setLocalNumber(flatDTO.getLocalNumber());
        address.setPostCode(flatDTO.getPostCode());
        address.setStreet(flatDTO.getStreet());
        return address;
    }

    @Override
    public Address createAddressObject(HouseDTO houseDTO) {
        Address address = new Address();
        address.setCity(houseDTO.getCity());
        address.setHomeNumber(houseDTO.getHouseNumber());
        address.setLocalNumber(houseDTO.getLocalNumber());
        address.setPostCode(houseDTO.getPostCode());
        address.setStreet(houseDTO.getStreet());
        return address;
    }

    @Override
    public Address createAddressObject(PlotDTO plotDTO) {
        Address address = new Address();
        address.setCity(plotDTO.getCity());
        address.setHomeNumber(plotDTO.getHouseNumber());
        address.setLocalNumber(plotDTO.getLocalNumber());
        address.setPostCode(plotDTO.getPostCode());
        address.setStreet(plotDTO.getStreet());
        return address;
    }

    @Override
    public List<Address> getAddress(Address address){
        return addressDAO.getAddress(address);
    }
}
