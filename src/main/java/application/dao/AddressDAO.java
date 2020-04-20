package application.dao;

import application.model.Address;
import java.util.List;

public interface AddressDAO {
    Address save(Address address);
    Address findById(int id);
    List<Address> verifyAddress(Address address);
}
