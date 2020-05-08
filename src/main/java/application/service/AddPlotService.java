package application.service;

import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;

public interface AddPlotService {
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
}
