package application.dao;

import application.model.Finance;
import application.model.House;
import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class FinanceDAOImpl implements FinanceDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public Finance save(Finance finance) {
        return em.merge(finance);
    }

    @Transactional(readOnly = true)
    @Override
    public Finance findByUserId(int userId) {
        try {
            return em.createNamedQuery(Finance.GET_BY_USER_ID, Finance.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
