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

    private final AddressService addressService;
    private final HouseDAO houseDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;

    public HouseServiceImpl(AddressService addressService, UserRealAssetsService userRealAssetsService, HouseDAO houseDAO, UserDAO userDAO) {
        this.addressService = addressService;
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
    }

    @Override
    public ResponseEntity<Object> verifyAddress(HouseDTO houseDTO) {
        Address address=addressService.createAddressObject(houseDTO, PropertyType.HOUSE,PropertyType.ID_HOUSE);  //tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(houseDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO

        if (addressService.getAddress(address).size()!=0) {
            //jeśli w bazie istnieje już taki adres oraz jeśli istniejący adres to dom lub mieszkanie - nie możemy dodać domu pod tym adresem
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        } else {//jeśli adres nie istnieje w bazie - dodajemy dom oraz wpis w userrealassets
            return addHouse(address,user,houseDTO);
        }
    }

    private ResponseEntity<Object> addHouse(Address address, User user, HouseDTO houseDTO){
        House house = saveHouse(address, user, houseDTO);
        userRealAssetsService.saveUserRealAsset(user,house);
        return new ResponseEntity<>(house, HttpStatus.CREATED);
    }

    private House saveHouse(Address address, User user, HouseDTO houseDTO) {
        House house = new House();
        house.setAddress(address);
        house.setUser(user);
        house.setSize(houseDTO.getSize());
        house.setPrice(houseDTO.getPrice());
        house.setRooms(houseDTO.getRooms());
        house.setSold(false);
        return houseDAO.save(house);
    }
}
