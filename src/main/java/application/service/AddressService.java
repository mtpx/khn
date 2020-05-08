package application.service;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Address;
import java.util.List;

public interface AddressService {
    Address createAddressObject(FlatDTO flatDTO);
    Address createAddressObject(HouseDTO houseDTO);
    Address createAddressObject(PlotDTO plotDTO);
    List<Address> getAddress(Address address);
}
