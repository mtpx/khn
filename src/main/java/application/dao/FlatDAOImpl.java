package application.dao;

import application.model.Flat;
import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class FlatDAOImpl implements FlatDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public Flat save(Flat flat) {
        return em.merge(flat);
    }

    @Transactional(readOnly = true)
    @Override
    public Flat findById(int id) {
        try {
            return em.find(Flat.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
