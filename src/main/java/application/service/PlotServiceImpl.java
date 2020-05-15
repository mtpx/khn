package application.service;

import application.dao.*;
import application.dto.PlotDTO;
import application.model.*;
import application.model.enums.PropertyType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlotServiceImpl implements PlotService {
    final static Logger LOGGER = Logger.getLogger(PlotServiceImpl.class.getName());

    private final AddressDAO addressDAO;
    private final HouseDAO houseDAO;
    private final PlotDAO plotDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;

    public PlotServiceImpl(AddressDAO addressDAO, UserRealAssetsService userRealAssetsService, HouseDAO houseDAO, PlotDAO plotDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.houseDAO = houseDAO;
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
    }

    @Override
    public ResponseEntity<Object> verifyAddress(PlotDTO plotDTO) {
        Address address= createAddressObject(plotDTO);//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(plotDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressDAO.getAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać

        if(existingAddresses.size()==0) { //jeśli adres nie istnieje w bazie - dodajemy działkę oraz wpis w userrealassets
            return addPlot(address,user,plotDTO);
        }else if(existingAddresses.size()>1){  //jeśli w bazie pod tym adresem istnieją 2 takie same powiązane ze sobą nieruchomośc (czyli działka+dom) - nie możemy dodać kolejnej działki
            return new ResponseEntity<>("home at this address have assigned plot", HttpStatus.BAD_REQUEST);
        }else{ //jeśli jeden taki adres istnieje w bazie
            return verifyPropertyOnAddress(existingAddresses.get(0),user,plotDTO,address);
        }
    }

    private ResponseEntity<Object> addPlot(Address address, User user, PlotDTO plotDTO){
        Plot plot = savePlot(address, user, plotDTO);
        userRealAssetsService.saveUserRealAssets(user, plot);
        return new ResponseEntity<>(plot, HttpStatus.CREATED);
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

    private Plot addHouseToPlot(House house, Plot plot){
        plot.setHouse(house);
        return plotDAO.save(plot);
    }

    private Address createAddressObject(PlotDTO plotDTO){
        Address address = new Address();
        address.setCity(plotDTO.getCity());
        address.setHomeNumber(plotDTO.getHouseNumber());
        address.setLocalNumber(plotDTO.getLocalNumber());
        address.setPostCode(plotDTO.getPostCode());
        address.setStreet(plotDTO.getStreet());
        address.setRealAssets(new RealAssets(PropertyType.ID_PLOT,PropertyType.PLOT));
        return address;
    }

    private void assignPlotToHouse(House house, Plot plot, UserRealAssets userRealAssets){
        addHouseToPlot(house, plot); //dodajemy do rekordu dodanej działki dom który na niej stoi
        userRealAssetsService.addPlotToUserRealAssets(userRealAssets, plot); //aktualizujemy wpis o działkę w userrealassets
    }

    private ResponseEntity<Object> verifyPropertyOnAddress(Address existingAddresses, User user, PlotDTO plotDTO, Address newAddress){
        if (!existingAddresses.getRealAssets().getType().equals("house")) { //jeśli istniejący adres to nie dom, czyli mieszkanie/działka - nie możemy dodać kolejnej działki pod tym adresem
            return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
        }else { //jeśli istniejący adres to dom - możemy podpiąć pod niego działkę
            return addPlotToHouse(existingAddresses,user,plotDTO, newAddress);
        }
    }

    private ResponseEntity<Object> addPlotToHouse(Address existingAddresses, User user, PlotDTO plotDTO, Address newAddress){
        House house = houseDAO.findByAddressId(existingAddresses.getId()); //pobieramy dom na podstawie adresu który został podany przy dodawaniu działki

        Plot plot = savePlot(newAddress, user, plotDTO); //zapisujemy działkę do bazy
        assignPlotToHouse(house,plot,userRealAssetsService.getByHouseId(house.getId()));
        return new ResponseEntity<>(plot, HttpStatus.CREATED);
    }
}
