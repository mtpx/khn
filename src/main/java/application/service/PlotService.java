package application.service;

import application.dto.PlotDTO;
import org.springframework.http.ResponseEntity;

public interface PlotService {
    ResponseEntity<Object> addPlot(PlotDTO plotDTO);
}
