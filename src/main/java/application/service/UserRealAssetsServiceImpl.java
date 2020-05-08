package application.service;

import application.dao.UserRealAssetsDAO;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserRealAssetsServiceImpl implements UserRealAssetsService {
    final static Logger LOGGER = Logger.getLogger(UserRealAssetsServiceImpl.class.getName());
    private final UserRealAssetsDAO userRealAssetsDAO;

    public UserRealAssetsServiceImpl(UserRealAssetsDAO userRealAssetsDAO) {
        this.userRealAssetsDAO = userRealAssetsDAO;
    }

    @Override
    public UserRealAssets saveUserRealAsset(User user, Flat flat){ //dodawanie wpisu z mieszkaniem w userrealassets
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setFlat(flat);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets saveUserRealAsset(User user, House house){ //dodawanie wpisu z domem w userrealassets
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setHouse(house);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets saveUserRealAssets(User user, Plot plot){ //dodawanie wpisu z działką w userrealassets
        UserRealAssets userRealAssets = new UserRealAssets();
        userRealAssets.setUser(user);
        userRealAssets.setPlot(plot);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets assignNewOwnerToFlatInUserRealAssets(Flat flat, User customer){ //zmiana właściela mieszkania w userrealassets
        UserRealAssets userRealAssets = getByFlatId(flat.getId());
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets assignNewOwnerToHouseInUserRealAssets(House house, User customer){ //zmiana właściela domu w userrealassets
        UserRealAssets userRealAssets = userRealAssetsDAO.getByHouseId(house.getId());
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets assignNewOwnerToPlotInUserRealAssets(Plot plot, User customer){ //zmiana właściela działki w userrealassets
        UserRealAssets userRealAssets = userRealAssetsDAO.getByPlotId(plot.getId());
        userRealAssets.setUser(customer);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets addPlotToUserRealAssets(UserRealAssets userRealAssets, Plot plot){ //dodawanie działki do rekordu z domem
        userRealAssets.setPlot(plot);
        return userRealAssetsDAO.save(userRealAssets);
    }

    @Override
    public UserRealAssets getByHouseId(int houseId) {
        return userRealAssetsDAO.getByHouseId(houseId);
    }

    @Override
    public UserRealAssets getByFlatId(int flatId) {
        return userRealAssetsDAO.getByFlatId(flatId);
    }

    @Override
    public UserRealAssets getByPlotId(int plotId) {
        return userRealAssetsDAO.getByPlotId(plotId);
    }
}
