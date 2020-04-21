package application.dao;

import application.model.UserRealAssets;

public interface UserRealAssetsDAO {
    UserRealAssets save(UserRealAssets userRealAssets);
    UserRealAssets getByHouseId(int houseId);
}
