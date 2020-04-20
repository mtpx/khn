package application.dao;

import application.model.User;
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
            return em.createNamedQuery(AuctionView.GET_PROPERTIES, AuctionView.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}

