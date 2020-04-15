package application.controller.api;

import application.dao.AddressDAO;
import application.dao.UserDAO;
import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.dto.PropertyBaseDTO;
import application.model.*;
import application.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "KHN property API controller")
@RestController
public class APIPropertyController {

    private PropertyService propertyService;
    private AddressDAO addressDAO;
    private UserDAO userDAO;

    public APIPropertyController(PropertyService propertyService, UserDAO userDAO, AddressDAO addressDAO) {
        this.propertyService = propertyService;
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
    }

//    @ApiOperation(value = "Adding property", response = Address.class)
//    @PostMapping(value = "/property")
//    public ResponseEntity<Object> addProperty(@RequestBody @Valid PropertyBaseDTO propertyBaseDTO){
//        return propertyService.addProperty(propertyBaseDTO);
//    }

    @ApiOperation(value = "Adding house", response = House.class)
    @PostMapping(value = "/property/house")
    public ResponseEntity<Object> addHouse(@RequestBody @Valid HouseDTO houseDTO){
        return propertyService.addHouse(houseDTO);
    }

    @ApiOperation(value = "Adding flat", response = Flat.class)
    @PostMapping(value = "/property/flat")
    public ResponseEntity<Object> addFlat(@RequestBody @Valid FlatDTO flatDTO){
        return propertyService.addFlat(flatDTO);
    }

    @ApiOperation(value = "Adding plot", response = Plot.class)
    @PostMapping(value = "/property/plot")
    public ResponseEntity<Object> addPlot(@RequestBody @Valid PlotDTO plotDTO){
        return propertyService.addPlot(plotDTO);
    }

}
