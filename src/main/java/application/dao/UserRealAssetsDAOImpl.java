package application.dao;

import application.model.User;
import application.model.UserRealAssets;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class UserRealAssetsDAOImpl implements UserRealAssetsDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public UserRealAssets save(UserRealAssets userRealAssets) {
        return em.merge(userRealAssets);
    }

    @Transactional(readOnly = true)
    @Override
    public UserRealAssets getByHouseId(int houseId) {
        try {
            return em.createNamedQuery(UserRealAssets.GET_BY_HOUSE_ID, UserRealAssets.class)
                    .setParameter("houseId", houseId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserRealAssets getByFlatId(int flatId) {
        try {
            return em.createNamedQuery(UserRealAssets.GET_BY_FLAT_ID, UserRealAssets.class)
                    .setParameter("flatId", flatId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserRealAssets getByPlotId(int plotId) {
        try {
            return em.createNamedQuery(UserRealAssets.GET_BY_PLOT_ID, UserRealAssets.class)
                    .setParameter("plotId", plotId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public UserRealAssets delete(UserRealAssets userRealAssets) {
        try {
            em.remove(userRealAssets);
            return userRealAssets;
        } catch (NoResultException e) {
            return null;
        }
    }

}
