package application.service.facades;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.service.FlatService;
import application.service.HouseService;
import application.service.PlotService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddPropertyFacadeImpl implements AddPropertyFacade {
    final static Logger LOGGER = Logger.getLogger(AddPropertyFacadeImpl.class.getName());

    private final FlatService flatService;
    private final HouseService houseService;
    private final PlotService plotService;

    public AddPropertyFacadeImpl(FlatService flatService, HouseService houseService, PlotService plotService) {
        this.flatService = flatService;
        this.houseService = houseService;
        this.plotService = plotService;
    }

    @Override
    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        return plotService.addPlot(plotDTO);
    }

    @Override
    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        return flatService.addFlat(flatDTO);
    }

    @Override
    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        return houseService.addHouse(houseDTO);
    }
}

