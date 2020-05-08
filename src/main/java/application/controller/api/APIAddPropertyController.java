package application.controller.api;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Flat;
import application.model.House;
import application.model.Plot;
import application.service.facades.AddPropertyFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "Adding property API controller")
@RestController
@RequestMapping("/property/add")
public class APIAddPropertyController {

    private AddPropertyFacade addPropertyFacade;

    public APIAddPropertyController(AddPropertyFacade addPropertyFacade) {
        this.addPropertyFacade = addPropertyFacade;
    }

    @ApiOperation(value = "Adding flat", response = Flat.class)
    @PostMapping(value = "/flat")
    public ResponseEntity<Object> addFlat(@RequestBody @Valid FlatDTO flatDTO){
        return addPropertyFacade.addFlat(flatDTO);
    }

    @ApiOperation(value = "Adding house", response = House.class)
    @PostMapping(value = "/house")
    public ResponseEntity<Object> addHouse(@RequestBody @Valid HouseDTO houseDTO){
        return addPropertyFacade.addHouse(houseDTO);
    }

    @ApiOperation(value = "Adding plot", response = Plot.class)
    @PostMapping(value = "/plot")
    public ResponseEntity<Object> addPlot(@RequestBody @Valid PlotDTO plotDTO){
        return addPropertyFacade.addPlot(plotDTO);
    }
}
