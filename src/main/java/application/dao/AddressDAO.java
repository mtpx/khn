package application.dao;

import application.model.Address;

public interface AddressDAO {
    Address save(Address address);
    Address findById(int id);
}
