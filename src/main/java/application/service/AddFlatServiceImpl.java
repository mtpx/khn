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
public class AddFlatServiceImpl implements AddFlatService {
    final static Logger LOGGER = Logger.getLogger(AddFlatServiceImpl.class.getName());
    private final FlatDAO flatDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;
    private final AddressService addressService;

    public AddFlatServiceImpl(UserRealAssetsService userRealAssetsService, FlatDAO flatDAO, UserDAO userDAO, AddressService addressService) {
        this.flatDAO = flatDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
        this.addressService = addressService;
    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        //Dwie linijki z address można jeszcze wyniesc do metody prywatnej
        Address address=addressService.createAddressObject(flatDTO);
        address.setRealAssets(new RealAssets(PropertyType.ID_FLAT,PropertyType.FLAT)); //tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(flatDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        if (addressService.getAddress(address).size()!=0) //jeśli w bazie istnieje podany adres nie możemy dodać mieszkania
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        else {//jeśli adres nie istnieje w bazie - dodajemy mieszkanie oraz wpis w userrealassets
            Flat flat = saveFlat(address, user, flatDTO);
            userRealAssetsService.saveUserRealAsset(user,flat);
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
}
