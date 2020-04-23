package application.service;

import application.dao.*;
import application.dto.*;
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
    public ResponseEntity<Object> findPropertiesByType(String propertyType) {
        return new ResponseEntity<>(auctionViewDAO.findByType(propertyType), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        Address address=createAddressObject(flatDTO.getStreet(),flatDTO.getHouseNumber(),flatDTO.getLocalNumber(),flatDTO.getPostCode(),flatDTO.getCity());
        address.setRealAssets(new RealAssets(1, "flat")); //tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(flatDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressDAO.verifyAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać
        if (existingAddresses.size() > 0) //jeśli w bazie istnieje podany adres nie możemy dodać mieszkania
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        else {//jeśli adres nie istnieje w bazie - dodajemy mieszkanie oraz wpis w userrealassets
            Flat flat = saveFlatObject(address, user, flatDTO);
            UserRealAssets userRealAssets = new UserRealAssets();
            userRealAssets.setUser(user);
            userRealAssets.setFlat(flat);
            userRealAssetsDAO.save(userRealAssets);
            return new ResponseEntity<>(flat, HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        Address address=createAddressObject(houseDTO.getStreet(),houseDTO.getHouseNumber(),houseDTO.getLocalNumber(),houseDTO.getPostCode(),houseDTO.getCity());
        address.setRealAssets(new RealAssets(2, "house"));//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(houseDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressDAO.verifyAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać
        if (existingAddresses.size() > 0 && (existingAddresses.get(0).getRealAssets().getType().equals("house") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))) {
            //jeśli w bazie istnieje już taki adres oraz jeśli istniejący adres to dom lub mieszkanie - nie możemy dodać domu pod tym adresem
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        } else {//jeśli adres nie istnieje w bazie - dodajemy dom oraz wpis w userrealassets
            House house = saveHouseObject(address, user, houseDTO);
            UserRealAssets userRealAssets = new UserRealAssets();
            userRealAssets.setUser(user);
            userRealAssets.setHouse(house);
            userRealAssetsDAO.save(userRealAssets);
            return new ResponseEntity<>(house, HttpStatus.CREATED);
        }
    }


    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        Address address=createAddressObject(plotDTO.getStreet(),plotDTO.getHouseNumber(),plotDTO.getLocalNumber(),plotDTO.getPostCode(),plotDTO.getCity());
        address.setRealAssets(new RealAssets(3, "plot"));//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(plotDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressDAO.verifyAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać
        Plot plot;
        //jeśli adres już istnieje i jest to działka lub mieszkanie - nie możemy dodać działki pod tym adresem
        if (addressDAO.verifyAddress(address).size() > 0 && existingAddresses.get(0).getRealAssets().getType().equals("plot") || existingAddresses.get(0).getRealAssets().getType().equals("flat"))
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        //jeśli pod istniejącym adresem mamy dom
        else if (addressDAO.verifyAddress(address).size() > 0 && existingAddresses.get(0).getRealAssets().getType().equals("house")) {
            House house = houseDAO.findByAddressId(existingAddresses.get(0).getId());
            UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(house.getId());
            if (userRealAssets.getPlot() != null) //jeśli do domu jest przypisana już działka - nie możemy przypisać kolejnej
                return new ResponseEntity<>("home at this address have assigned plot", HttpStatus.BAD_REQUEST);
            //jeśli adres już istnieje, jest to dom który nie ma przypisanej działki - dodajemy ją do bazy, wiążemy z domem  oraz aktualizujemy rekord w userrealassets
            plot = savePlotObject(address, user, plotDTO);
            plot.setHouse(houseDAO.findByAddressId(existingAddresses.get(0).getId()));
            plot.setAddress(addressDAO.findById(existingAddresses.get(0).getId()));
            userRealAssets.setPlot(plot);
            userRealAssetsDAO.save(userRealAssets);
            return new ResponseEntity<>(plot, HttpStatus.CREATED);
        } else { //jeśli adres nie istnieje w bazie - dodajemy działkę oraz wpis w userrealassets
            plot = savePlotObject(address, user, plotDTO);
            UserRealAssets userRealAssets = new UserRealAssets();
            userRealAssets.setUser(user);
            userRealAssets.setPlot(plot);
            userRealAssetsDAO.save(userRealAssets);
            return new ResponseEntity<>(plot, HttpStatus.CREATED);
        }
    }

    public Flat saveFlatObject(Address address, User user, FlatDTO flatDTO) {
        Flat flat = new Flat();
        flat.setFloor(flatDTO.getFloor());
        flat.setAddress(address);
        flat.setUser(user);
        flat.setSize(flatDTO.getSize());
        flat.setPrice(flatDTO.getPrice());
        flat.setRooms(flatDTO.getRooms());
        return flatDAO.save(flat);
    }

    public Plot savePlotObject(Address address, User user, PlotDTO plotDTO) {
        Plot plot = new Plot();
        plot.setAddress(address);
        plot.setUser(user);
        plot.setSize(plotDTO.getSize());
        plot.setPrice(plotDTO.getPrice());
        plot.setType(plotDTO.getType());
        return plotDAO.save(plot);
    }

    public House saveHouseObject(Address address, User user, HouseDTO houseDTO) {
        House house = new House();
        house.setAddress(address);
        house.setUser(user);
        house.setSize(houseDTO.getSize());
        house.setPrice(houseDTO.getPrice());
        house.setRooms(houseDTO.getRooms());
        return houseDAO.save(house);
    }

    public Address createAddressObject(String street, int houseNumber, int localNumber, String postCode, String city) {
        Address address = new Address();
        address.setCity(city);
        address.setHomeNumber(houseNumber);
        address.setLocalNumber(localNumber);
        address.setPostCode(postCode);
        address.setStreet(street);
        return address;
    }
}
