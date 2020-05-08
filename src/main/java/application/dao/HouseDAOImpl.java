package application.dao;

import application.model.House;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class HouseDAOImpl implements HouseDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public House save(House house) {
        return em.merge(house);
    }

    @Transactional(readOnly = true)
    @Override
    public House findById(int id) {
        try {
            return em.find(House.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public House findByAddressId(int addressId) {
        try {
            return em.createNamedQuery(House.GET_BY_ADDRESSID, House.class)
                    .setParameter("addressId", addressId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
