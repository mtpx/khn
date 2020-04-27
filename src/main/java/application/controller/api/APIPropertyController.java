package application.controller.api;

import application.dto.TransactionDTO;
import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Flat;
import application.model.House;
import application.model.Plot;
import application.model.views.AuctionView;
import application.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "KHN property API controller")
@RestController
public class APIPropertyController {

    private AuctionViewService auctionViewService;
    private PropertyFacadeService propertyFacadeService;

    public APIPropertyController(AuctionViewService auctionViewService, PropertyFacadeService propertyFacadeService) {
        this.auctionViewService = auctionViewService;
        this.propertyFacadeService = propertyFacadeService;
    }

    @ApiOperation(value = "Adding flat", response = Flat.class)
    @PostMapping(value = "/property/flat")
    public ResponseEntity<Object> addFlat(@RequestBody @Valid FlatDTO flatDTO){
        return propertyFacadeService.addFlat(flatDTO);
    }

    @ApiOperation(value = "Adding house", response = House.class)
    @PostMapping(value = "/property/house")
    public ResponseEntity<Object> addHouse(@RequestBody @Valid HouseDTO houseDTO){
        return propertyFacadeService.addHouse(houseDTO);
    }

    @ApiOperation(value = "Adding plot", response = Plot.class)
    @PostMapping(value = "/property/plot")
    public ResponseEntity<Object> addPlot(@RequestBody @Valid PlotDTO plotDTO){
        return propertyFacadeService.addPlot(plotDTO);
    }


    @ApiOperation(value = "Adding plot")
    @PostMapping(value = "/property/transaction")
    public ResponseEntity<Object> buyProperty(@RequestBody @Valid TransactionDTO buyPropertyDTO){
        return propertyFacadeService.executeTransaction(buyPropertyDTO);
    }



    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/auctionView")
    public ResponseEntity<Object> findAllProperties(){
        return auctionViewService.findAllProperties();
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/auctionView/flat")
    public ResponseEntity<Object> findFlats(){
        return auctionViewService.findPropertiesByType("flat");
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/auctionView/plot")
    public ResponseEntity<Object> findPlots(){
        return auctionViewService.findPropertiesByType("plot");
    }

    @ApiOperation(value = "Get all properties", response = AuctionView.class)
    @GetMapping(value = "/auctionView/house")
    public ResponseEntity<Object> findHouses(){
        return auctionViewService.findPropertiesByType("house");
    }

}
