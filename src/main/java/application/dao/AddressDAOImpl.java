package application.dao;

import application.model.Address;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

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

    @Override
    public Address getAddress(Address address) {
        try {
            return em.createNamedQuery(Address.VERIFY_ADDRESS, Address.class)
                    .setParameter("street", address.getStreet())
                    .setParameter("homeNumber", address.getHomeNumber())
                    .setParameter("localNumber", address.getLocalNumber())
                    .setParameter("postCode", address.getPostCode())
                    .setParameter("city", address.getCity())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
