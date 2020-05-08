package application.dao;

import application.model.views.AuctionView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository
public class AuctionViewDAOImpl implements AuctionViewDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<AuctionView> findAll() {
        try {
            List<AuctionView> av = em.createNamedQuery(AuctionView.GET_PROPERTIES, AuctionView.class)
                    .getResultList();
            return av;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuctionView> findByType(String propertyType) {
        try {
            List<AuctionView> av= em.createNamedQuery(AuctionView.GET_PROPERTIES_BY_TYPE, AuctionView.class)
                    .setParameter("type", propertyType)
                    .getResultList();
            return av;
        } catch (NoResultException e) {
            return null;
        }
    }

}

