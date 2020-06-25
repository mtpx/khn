package application.dao;

import application.model.Address;
import application.model.Credit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressDAO {
    Address save(Address address);
    Address findById(int id);
    List<Address> getAddress(Address address);
}
