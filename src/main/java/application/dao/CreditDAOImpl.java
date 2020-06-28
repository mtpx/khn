package application.dao;

import application.model.Credit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class CreditDAOImpl implements CreditDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public Credit save(Credit credit) {
        return em.merge(credit);
    }

    @Transactional(readOnly = true)
    @Override
    public Credit findById(int id) {
        try {
            return em.find(Credit.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

}
