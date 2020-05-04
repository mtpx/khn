package application.controller.api;
import application.model.views.AuctionView;
import application.service.AuctionViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "KHN property API controller")
@RestController
@RequestMapping("/property/view")
public class APIViewPropertyController {

    private AuctionViewService auctionViewService;

    public APIViewPropertyController(AuctionViewService auctionViewService) {
        this.auctionViewService = auctionViewService;
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/all")
    public ResponseEntity<Object> findAllProperties(){
        return auctionViewService.findAllProperties();
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/flat")
    public ResponseEntity<Object> findFlats(){
        return auctionViewService.findPropertiesByType("flat");
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/plot")
    public ResponseEntity<Object> findPlots(){
        return auctionViewService.findPropertiesByType("plot");
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/house")
    public ResponseEntity<Object> findHouses(){
        return auctionViewService.findPropertiesByType("house");
    }

}
