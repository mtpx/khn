package application.dao;

import application.model.Plot;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class PlotDAOImpl implements PlotDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public Plot save(Plot plot) {
        return em.merge(plot);
    }

    @Transactional(readOnly = true)
    @Override
    public Plot findById(int id) {
        try {
            return em.find(Plot.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
