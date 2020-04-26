package application.service;

import application.dao.*;
import application.dto.PlotDTO;
import application.model.*;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlotServiceImpl implements PlotService {
    final static Logger LOGGER = Logger.getLogger(PlotServiceImpl.class.getName());

    private final AddressDAO addressDAO;
    private final HouseDAO houseDAO;
    private final PlotDAO plotDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsDAO userRealAssetsDAO;

    public PlotServiceImpl(AddressDAO addressDAO, UserRealAssetsDAO userRealAssetsDAO, HouseDAO houseDAO, PlotDAO plotDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.houseDAO = houseDAO;
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsDAO = userRealAssetsDAO;
    }

    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        Address address=createAddressObject(plotDTO);
        address.setRealAssets(new RealAssets(PropertyType.ID_PLOT, PropertyType.PLOT));//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(plotDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        Address existingAddresses = addressDAO.getAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać
        Plot plot;
        if(existingAddresses==null) { //jeśli adres nie istnieje w bazie - dodajemy działkę oraz wpis w userrealassets
            plot = savePlot(address, user, plotDTO);
            saveUserRealAssets(user, plot);
            return new ResponseEntity<>(plot, HttpStatus.CREATED);
        }else{ //jeśli adres istnieje w bazie
            if (!existingAddresses.getRealAssets().getType().equals("house")) { //jeśli istniejący adres to nie dom, czyli mieszkanie/działka
                return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
            }else{ //jeśli istniejący adres to dom - możemy podpiąć pod niego działkę
                return assignPlotToHouse(existingAddresses,user,plotDTO);
            }
        }
    }


    private Plot savePlot(Address address, User user, PlotDTO plotDTO) {
        Plot plot = new Plot();
        plot.setAddress(address);
        plot.setUser(user);
        plot.setSize(plotDTO.getSize());
        plot.setPrice(plotDTO.getPrice());
        plot.setType(plotDTO.getType());
        return plotDAO.save(plot);
    }

    private Address createAddressObject(PlotDTO plotDTO) {
        Address address = new Address();
        address.setCity(plotDTO.getCity());
        address.setHomeNumber(plotDTO.getHouseNumber());
        address.setLocalNumber(plotDTO.getLocalNumber());
        address.setPostCode(plotDTO.getPostCode());
        address.setStreet(plotDTO.getStreet());
        return address;
    }

    private UserRealAssets saveUserRealAssets(User user, Plot plot){
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setPlot(plot);
        return userRealAssetsDAO.save(userRealAssets);
    }

    private UserRealAssets addPlotToUserRealAssets(UserRealAssets userRealAssets, Plot plot){
        userRealAssets.setPlot(plot);
        return userRealAssetsDAO.save(userRealAssets);
    }

    private ResponseEntity<Object> assignPlotToHouse(Address existingAddresses, User user, PlotDTO plotDTO){
        House house = houseDAO.findByAddressId(existingAddresses.getId());
        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(house.getId());
        if (userRealAssets.getPlot() != null) //jeśli do domu jest przypisana już działka - nie możemy przypisać kolejnej
            return new ResponseEntity<>("home at this address have assigned plot", HttpStatus.BAD_REQUEST);
        Plot plot = savePlot(existingAddresses, user, plotDTO);
        plot.setHouse(houseDAO.findByAddressId(existingAddresses.getId()));
        plot.setAddress(addressDAO.findById(existingAddresses.getId()));
        addPlotToUserRealAssets(userRealAssets,plot);
        return new ResponseEntity<>(plot, HttpStatus.CREATED);
    }
}
