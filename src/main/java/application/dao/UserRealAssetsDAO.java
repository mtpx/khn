package application.dao;

import application.model.UserRealAssets;

public interface UserRealAssetsDAO {
    UserRealAssets save(UserRealAssets userRealAssets);
    UserRealAssets getByHouseId(int houseId);
    UserRealAssets getByFlatId(int flatId);
    UserRealAssets getByPlotId(int plotId);
    UserRealAssets delete(UserRealAssets userRealAssets);
}
