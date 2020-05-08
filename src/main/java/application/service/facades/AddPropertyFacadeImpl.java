package application.service.facades;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.service.AddFlatService;
import application.service.AddHouseService;
import application.service.AddPlotService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddPropertyFacadeImpl implements AddPropertyFacade {
    final static Logger LOGGER = Logger.getLogger(AddPropertyFacadeImpl.class.getName());

    private final AddFlatService addFlatService;
    private final AddHouseService addHouseService;
    private final AddPlotService addPlotService;

    public AddPropertyFacadeImpl(AddFlatService addFlatService, AddHouseService addHouseService, AddPlotService addPlotService) {
        this.addFlatService = addFlatService;
        this.addHouseService = addHouseService;
        this.addPlotService = addPlotService;
    }

    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        return addPlotService.addPlot(plotDTO);
    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        return addFlatService.addFlat(flatDTO);
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        return addHouseService.addHouse(houseDTO);
    }
}

