package application.dao;

import application.model.House;

public interface HouseDAO {
    House save(House house);
    House findById(int id);
    House findByAddressId(int addressId);

}
