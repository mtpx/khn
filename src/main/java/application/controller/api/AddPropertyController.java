package application.controller.api;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.facades.PropertyFacade;
import application.model.Flat;
import application.model.House;
import application.model.Plot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "Adding property API controller")
@RestController
@RequestMapping("/property/add")
public class AddPropertyController {

    private PropertyFacade propertyFacade;

    public AddPropertyController(PropertyFacade propertyFacade) {
        this.propertyFacade = propertyFacade;
    }

    @ApiOperation(value = "Adding flat", response = Flat.class)
    @PostMapping(value = "/flat")
    public ResponseEntity<Object> addFlat(@RequestBody @Valid FlatDTO flatDTO){
        return propertyFacade.addFlat(flatDTO);
    }

    @ApiOperation(value = "Adding house", response = House.class)
    @PostMapping(value = "/house")
    public ResponseEntity<Object> addHouse(@RequestBody @Valid HouseDTO houseDTO){
        return propertyFacade.addHouse(houseDTO);
    }

    @ApiOperation(value = "Adding plot", response = Plot.class)
    @PostMapping(value = "/plot")
    public ResponseEntity<Object> addPlot(@RequestBody @Valid PlotDTO plotDTO){
        return propertyFacade.addPlot(plotDTO);
    }
}
