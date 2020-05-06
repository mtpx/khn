package application.service;

import application.dao.*;
import application.dto.FlatDTO;
import application.model.*;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlatServiceImpl implements FlatService {
    final static Logger LOGGER = Logger.getLogger(FlatServiceImpl.class.getName());
    private final AddressDAO addressDAO;
    private final FlatDAO flatDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;

    public FlatServiceImpl(AddressDAO addressDAO, UserRealAssetsDAO userRealAssetsDAO, FlatDAO flatDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.flatDAO = flatDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        Address address=createAddressObject(flatDTO);
        address.setRealAssets(new RealAssets(PropertyType.ID_FLAT,PropertyType.FLAT)); //tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(flatDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        if (addressDAO.getAddress(address).size()!=0) //jeśli w bazie istnieje podany adres nie możemy dodać mieszkania
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        else {//jeśli adres nie istnieje w bazie - dodajemy mieszkanie oraz wpis w userrealassets
            Flat flat = saveFlat(address, user, flatDTO);
            saveUserRealAsset(user,flat);
            return new ResponseEntity<>(flat, HttpStatus.CREATED);
        }
    }

    private Flat saveFlat(Address address, User user, FlatDTO flatDTO) {
        Flat flat = new Flat();
        flat.setFloor(flatDTO.getFloor());
        flat.setAddress(address);
        flat.setUser(user);
        flat.setSize(flatDTO.getSize());
        flat.setPrice(flatDTO.getPrice());
        flat.setRooms(flatDTO.getRooms());
        return flatDAO.save(flat);
    }

    private Address createAddressObject(FlatDTO flatDTO) {
        Address address = new Address();
        address.setCity(flatDTO.getCity());
        address.setHomeNumber(flatDTO.getHouseNumber());
        address.setLocalNumber(flatDTO.getLocalNumber());
        address.setPostCode(flatDTO.getPostCode());
        address.setStreet(flatDTO.getStreet());
        return address;
    }

    private UserRealAssets saveUserRealAsset(User user, Flat flat){
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setFlat(flat);
        return userRealAssetsDAO.save(userRealAssets);
    }
}
