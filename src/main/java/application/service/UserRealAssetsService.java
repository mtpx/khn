package application.service;

import application.model.*;

public interface UserRealAssetsService {
    void saveUserRealAsset(User user, Flat flat);
    void saveUserRealAsset(User user, House house);
    void saveUserRealAssets(User user, Plot plot);

    UserRealAssets assignNewOwnerToFlat(Flat flat, User customer);
    UserRealAssets assignNewOwnerToHouse(House house, User customer);
    UserRealAssets assignNewOwnerToPlot(Plot plot, User customer);

    UserRealAssets addPlotToUserRealAssets(UserRealAssets userRealAssets, Plot plot);

    UserRealAssets getByHouseId(int houseId);
    UserRealAssets getByFlatId(int flatId);
    UserRealAssets getByPlotId(int plotId);
}
