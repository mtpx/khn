package application.service;

import application.dao.AddressDAO;
import application.dto.AddressDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Address;
import application.model.RealAssets;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
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
    public Address createAddressObject(AddressDTO addressDTO, String propertyType, int propertyTypeId){
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setHomeNumber(addressDTO.getHouseNumber());
        address.setLocalNumber(addressDTO.getLocalNumber());
        address.setPostCode(addressDTO.getPostCode());
        address.setStreet(addressDTO.getStreet());
        address.setRealAssets(new RealAssets(propertyTypeId,propertyType));
        return address;
    }

    @Override
    public List<Address> getAddress(Address address){
        return addressDAO.getAddress(address);
    }

}
