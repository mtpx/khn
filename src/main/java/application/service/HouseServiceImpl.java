package application.service;

import application.dao.*;
import application.dto.HouseDTO;
import application.model.*;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    final static Logger LOGGER = Logger.getLogger(HouseServiceImpl.class.getName());

    private final AddressDAO addressDAO;
    private final HouseDAO houseDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;

    public HouseServiceImpl(AddressDAO addressDAO, UserRealAssetsDAO userRealAssetsDAO, HouseDAO houseDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        Address address=createAddressObject(houseDTO);
        address.setRealAssets(new RealAssets(PropertyType.ID_HOUSE, PropertyType.HOUSE));//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(houseDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        if (addressDAO.getAddress(address)!=null) {
            //jeśli w bazie istnieje już taki adres oraz jeśli istniejący adres to dom lub mieszkanie - nie możemy dodać domu pod tym adresem
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        } else {//jeśli adres nie istnieje w bazie - dodajemy dom oraz wpis w userrealassets
            House house = saveHouse(address, user, houseDTO);
            saveUserRealAsset(user,house);
            return new ResponseEntity<>(house, HttpStatus.CREATED);
        }
    }

    private House saveHouse(Address address, User user, HouseDTO houseDTO) {
        House house = new House();
        house.setAddress(address);
        house.setUser(user);
        house.setSize(houseDTO.getSize());
        house.setPrice(houseDTO.getPrice());
        house.setRooms(houseDTO.getRooms());
        return houseDAO.save(house);
    }

    private Address createAddressObject(HouseDTO houseDTO) {
        Address address = new Address();
        address.setCity(houseDTO.getCity());
        address.setHomeNumber(houseDTO.getHouseNumber());
        address.setLocalNumber(houseDTO.getLocalNumber());
        address.setPostCode(houseDTO.getPostCode());
        address.setStreet(houseDTO.getStreet());
        return address;
    }
    private UserRealAssets saveUserRealAsset(User user, House house){
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setHouse(house);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
