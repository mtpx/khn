package application.controller.api;

import application.dao.AddressDAO;
import application.dao.UserDAO;
import application.dto.PropertyDTO;
import application.model.views.AuctionView;
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

    @ApiOperation(value = "Adding property")
    @PostMapping(value = "/property")
    public ResponseEntity<Object> addProperty(@RequestBody @Valid PropertyDTO propertyDTO){
        return propertyService.addProperty(propertyDTO);
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/property")
    public ResponseEntity<Object> findAllProperties(){
        return propertyService.findAllProperties();
    }

}
