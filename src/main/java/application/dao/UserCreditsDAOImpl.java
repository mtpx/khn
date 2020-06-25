package application.dao;

import application.model.UserCredits;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class UserCreditsDAOImpl implements UserCreditsDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public UserCredits save(UserCredits userCredits) {
        return em.merge(userCredits);
    }

    @Transactional(readOnly = true)
    @Override
    public UserCredits findById(int id) {
        try {
            return em.find(UserCredits.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

}
