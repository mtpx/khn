package application.dao;

import application.model.views.AuctionView;
import java.util.List;

public interface AuctionViewDAO {
    List<AuctionView> findAll();
    List<AuctionView> findByType(String propertyType);
}
