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
public class AddPlotServiceImpl implements AddPlotService {
    final static Logger LOGGER = Logger.getLogger(AddPlotServiceImpl.class.getName());

    private final AddressService addressService;
    private final HouseDAO houseDAO;
    private final PlotDAO plotDAO;
    private final UserDAO userDAO;
    private final UserRealAssetsService userRealAssetsService;

    public AddPlotServiceImpl(AddressService addressService, UserRealAssetsService userRealAssetsService, HouseDAO houseDAO, PlotDAO plotDAO, UserDAO userDAO) {
        this.addressService = addressService;
        this.houseDAO = houseDAO;
        this.plotDAO = plotDAO;
        this.userDAO = userDAO;
        this.userRealAssetsService = userRealAssetsService;
    }

    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        //ogólnie można by było troche uprosić te warunki, ale tak przynajmniej mam jasne komunikaty przekazywane na front odnośnie tego co się zadziało
        Address address=addressService.createAddressObject(plotDTO);
        address.setRealAssets(new RealAssets(PropertyType.ID_PLOT, PropertyType.PLOT));//tworzymy obiekt z adresem na podstawie danych z DTO
        User user = userDAO.findById(plotDTO.getUserId()); //pobieramy użytkownika zawartego w propertyDTO
        List<Address> existingAddresses = addressService.getAddress(address); //pobieramy listę adresów takich jak ten który chcemy dodać
        Plot plot;
        if(existingAddresses.size()==0) { //jeśli adres nie istnieje w bazie - dodajemy działkę oraz wpis w userrealassets
            plot = savePlot(address, user, plotDTO);
            userRealAssetsService.saveUserRealAssets(user, plot);
            return new ResponseEntity<>(plot, HttpStatus.CREATED);
        }else if(existingAddresses.size()>1){  //jeśli w bazie pod tym adresem istnieją 2 takie same powiązane ze sobą nieruchomośc (czyli działka+dom) - nie możemy dodać kolejnej działki
            return new ResponseEntity<>("home at this address have assigned plot", HttpStatus.BAD_REQUEST);
        }else{ //jeśli jeden taki adres istnieje w bazie
            if (!existingAddresses.get(0).getRealAssets().getType().equals("house")) { //jeśli istniejący adres to nie dom, czyli mieszkanie/działka - nie możemy dodać kolejnej działki pod tym adresem
                return new ResponseEntity<>("property at this address exists", HttpStatus.BAD_REQUEST);
            }else{ //jeśli istniejący adres to dom - możemy podpiąć pod niego działkę
                return assignPlotToHouse(existingAddresses.get(0),user,plotDTO, address);
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

    private Plot addHouseToPlot(House house, Plot plot){
        plot.setHouse(house);
        return plotDAO.save(plot);
    }

    private ResponseEntity<Object> assignPlotToHouse(Address existingAddresses, User user, PlotDTO plotDTO, Address newAddress){
        House house = houseDAO.findByAddressId(existingAddresses.getId()); //pobieramy dom na podstawie adresu który został podany przy dodawaniu działki
        UserRealAssets userRealAssets = userRealAssetsService.getByHouseId(house.getId()); //pobieramy wpis z userrealassets zawierający rekord z domem do któego dodajemy działkę
        Plot plot = savePlot(newAddress, user, plotDTO); //zapisujemy działkę do bazy
        addHouseToPlot(house,plot); //dodajemy do rekordu dodanej działki dom który na niej stoi
        userRealAssetsService.addPlotToUserRealAssets(userRealAssets,plot); //aktualizujemy wpis o działkę w userrealassets
        return new ResponseEntity<>(plot, HttpStatus.CREATED);
    }
}
