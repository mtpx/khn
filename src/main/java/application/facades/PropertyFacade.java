package application.facades;

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
public class PropertyFacade {
    final static Logger LOGGER = Logger.getLogger(PropertyFacade.class.getName());

    private final FlatService flatService;
    private final HouseService houseService;
    private final PlotService plotService;

    public PropertyFacade(FlatService flatService, HouseService houseService, PlotService plotService) {
        this.flatService = flatService;
        this.houseService = houseService;
        this.plotService = plotService;
    }

    public ResponseEntity<Object> addPlot(PlotDTO plotDTO) {
        return plotService.verifyAddress(plotDTO);
    }

    public ResponseEntity<Object> addFlat(FlatDTO flatDTO) {
        return flatService.verifyAddress(flatDTO);
    }

    public ResponseEntity<Object> addHouse(HouseDTO houseDTO) {
        return houseService.verifyAddress(houseDTO);
    }
}

