package application.dao;

import application.model.House;
import application.model.Plot;

public interface PlotDAO {
    Plot save(Plot plot);
    Plot findById(int id);
}
