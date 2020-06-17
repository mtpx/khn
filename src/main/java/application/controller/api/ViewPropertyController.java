package application.controller.api;
import application.model.views.PropertyView;
import application.service.PropertyViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "Property view API controller")
@RestController
@RequestMapping("/property/view")
public class ViewPropertyController {

    private PropertyViewService propertyViewService;

    public ViewPropertyController(PropertyViewService propertyViewService) {
        this.propertyViewService = propertyViewService;
    }

    @ApiOperation(value = "Get all properties", response = PropertyView.class)
    @GetMapping(value = "/all")
    public List<PropertyView> findAllProperties(){
        return propertyViewService.findAllForSale();
    }

    @ApiOperation(value = "Get flat properties", response = PropertyView.class)
    @GetMapping(value = "/flat")
    public ResponseEntity<Object> findFlats(){
        return propertyViewService.findByTypeForSale("flat");
    }

    @ApiOperation(value = "Get plot properties", response = PropertyView.class)
    @GetMapping(value = "/plot")
    public ResponseEntity<Object> findPlots(){
        return propertyViewService.findByTypeForSale("plot");
    }

    @ApiOperation(value = "Get house properties", response = PropertyView.class)
    @GetMapping(value = "/house")
    public ResponseEntity<Object> findHouses(){
        return propertyViewService.findByTypeForSale("house");
    }

}
