package application.service;

import application.model.*;

public interface UserRealAssetsService {
    UserRealAssets saveUserRealAsset(User user, Flat flat);
    UserRealAssets saveUserRealAsset(User user, House house);
    UserRealAssets saveUserRealAssets(User user, Plot plot);

    UserRealAssets assignNewOwnerToFlatInUserRealAssets(Flat flat, User customer);
    UserRealAssets assignNewOwnerToHouseInUserRealAssets(House house, User customer);
    UserRealAssets assignNewOwnerToPlotInUserRealAssets(Plot plot, User customer);

    UserRealAssets addPlotToUserRealAssets(UserRealAssets userRealAssets, Plot plot);

    UserRealAssets getByHouseId(int houseId);
    UserRealAssets getByFlatId(int flatId);
    UserRealAssets getByPlotId(int plotId);
}
