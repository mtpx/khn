package application.service;

import application.dto.AddressDTO;
import application.model.Address;
import java.util.List;

public interface AddressService {


    Address createAddressObject(AddressDTO addressDTO, String PropertyType, int propertyTypeId);
    List<Address> getAddress(Address address);
}
