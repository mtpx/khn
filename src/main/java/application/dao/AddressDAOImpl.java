package application.dao;

import application.model.Address;
import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class AddressDAOImpl implements AddressDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public Address save(Address address) {
        return em.merge(address);
    }

    @Override
    public Address findById(int id) {
        try {
            return em.find(Address.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
