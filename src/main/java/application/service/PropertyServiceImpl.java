package application.service;

import application.dao.*;
import application.dto.PropertyDTO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    final static Logger LOGGER = Logger.getLogger(PropertyServiceImpl.class.getName());

    private final AddressDAO addressDAO;
    private final AuctionViewDAO auctionViewDAO;
    private final FlatDAO flatDAO;
    private final HouseDAO houseDAO;
    private final PlotDAO plotDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;

    public PropertyServiceImpl(AddressDAO addressDAO, UserRealAssetsDAO userRealAssetsDAO, AuctionViewDAO auctionViewDAO, FlatDAO flatDAO, HouseDAO houseDAO, PlotDAO plotDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.auctionViewDAO = auctionViewDAO;
        this.flatDAO = flatDAO;
        this.houseDAO = houseDAO;
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
    }

    @Override
    public ResponseEntity<Object> findAllProperties() {
        return new ResponseEntity<>(auctionViewDAO.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addProperty(PropertyDTO propertyDTO) {
        Address address = fillAddressObject(propertyDTO); //wypełniamy obiekt address adresem zawartym w propertyDTO
        User user = userDAO.findById(propertyDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressDAO.verifyAddress(address); //sprawdzamy czy w bazie istnieje adres zawarty w propertyDTO
        switch (propertyDTO.getRealAssetId()) {
            case 1: //flat
                Flat flat;
                if (existingAddresses.size() > 0) //jeśli w bazie istnieje podany adres nie możemy dodać mieszkania
                    return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
                else {//jeśli adres nie istnieje w bazie - dodajemy mieszkanie oraz wpis w userrealassets
                    flat = addFlat(address, user, propertyDTO);
                    addUserRealAssets(user, flat, "flat");
                    return new ResponseEntity<>(flat, HttpStatus.CREATED);
                }
            case 2: //house
                House house;
                if (existingAddresses.size() > 0) { //jeśli w bazie istnieje podany adres
                    //oraz jeśli istniejący adres to dom lub mieszkanie - nie możemy dodać domu pod tym adresem
                    if (existingAddresses.get(0).getRealAssets().getType().equals("house") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))
                        return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
                } else {//jeśli adres nie istnieje w bazie - dodajemy dom oraz wpis w userrealassets
                    house = addHouse(address, user, propertyDTO);
                    addUserRealAssets(user, house, "house");
                    return new ResponseEntity<>(house, HttpStatus.CREATED);
                }
            case 3: //plot
                Plot plot;
                if (addressDAO.verifyAddress(address).size() > 0) { //jeśli w bazie istnieje podany adres
                    //jeśli istniejący adres to działka lub mieszkanie - nie możemy dodać działki pod tym adresem
                    if (existingAddresses.get(0).getRealAssets().getType().equals("plot") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))
                        return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
                    if (existingAddresses.get(0).getRealAssets().getType().equals("house")) {  //jeśli pod istniejącym adresem mamy dom
                        house = houseDAO.findByAddressId(existingAddresses.get(0).getId());
                        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(house.getId());
                        if(userRealAssets.getPlot()!=null) //jeśli do domu jest przypisana już działka - nie możemy przypisać kolejnej
                            return new ResponseEntity<>("home at this address have assigned plot", HttpStatus.BAD_REQUEST);
                        //jeśli do domu nie jest jeszcze przypisana działka - dodajemy ją do bazy, wiążemy z domem  oraz aktualizujemy rekord w userrealassets
                        plot = addPlot(address, user, propertyDTO);
                        plot.setHouse(houseDAO.findByAddressId(existingAddresses.get(0).getId()));
                        plot.setAddress(addressDAO.findById(existingAddresses.get(0).getId()));
                        userRealAssets.setPlot(plot);
                        userRealAssetsDAO.save(userRealAssets);
                        return new ResponseEntity<>(plot, HttpStatus.CREATED);
                    }
                } else { //jeśli adres nie istnieje w bazie - dodajemy działkę oraz wpis w userrealassets
                    plot = addPlot(address, user, propertyDTO);
                    addUserRealAssets(user, plot, "plot");
                    return new ResponseEntity<>(plot, HttpStatus.CREATED);
                }
        }
        return new ResponseEntity<>("invalid realAssetId", HttpStatus.BAD_REQUEST);
    }

    private void addUserRealAssets(User user, Object object, String propertyType) {
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        switch (propertyType) {
            case "flat": userRealAssets.setFlat((Flat) object); break;
            case "house": userRealAssets.setHouse((House) object); break;
            case "plot":
                Plot plot = (Plot) object;
                userRealAssets.setPlot(plot);
                break;
        }
        userRealAssetsDAO.save(userRealAssets);
    }

    public Address fillAddressObject(PropertyDTO propertyDTO) {
        Address address = new Address();
        address.setCity(propertyDTO.getCity());
        address.setHomeNumber(propertyDTO.getHouseNumber());
        address.setLocalNumber(propertyDTO.getLocalNumber());
        address.setPostCode(propertyDTO.getPostCode());
        address.setStreet(propertyDTO.getStreet());
        switch (propertyDTO.getRealAssetId()) {
            case 1: address.setRealAssets(new RealAssets(1, "flat")); break;
            case 2: address.setRealAssets(new RealAssets(2, "house")); break;
            case 3: address.setRealAssets(new RealAssets(3, "plot")); break;
        }
        return address;
    }

    public Flat addFlat(Address address, User user, PropertyDTO propertyDTO) {
        Flat flat = new Flat();
        flat.setFloor(propertyDTO.getFloor());
        flat.setAddress(address);
        flat.setUser(user);
        flat.setSize(propertyDTO.getSize());
        flat.setPrice(propertyDTO.getPrice());
        flat.setRooms(propertyDTO.getRooms());
        return flatDAO.save(flat);
    }

    public Plot addPlot(Address address, User user, PropertyDTO propertyDTO) {
        Plot plot = new Plot();
        plot.setAddress(address);
        plot.setUser(user);
        plot.setSize(propertyDTO.getSize());
        plot.setPrice(propertyDTO.getPrice());
        plot.setType(propertyDTO.getType());
        return plotDAO.save(plot);
    }

    public House addHouse(Address address, User user, PropertyDTO propertyDTO) {
        House house = new House();
        house.setAddress(address);
        house.setUser(user);
        house.setSize(propertyDTO.getSize());
        house.setPrice(propertyDTO.getPrice());
        house.setRooms(propertyDTO.getRooms());
        return houseDAO.save(house);
    }
}
