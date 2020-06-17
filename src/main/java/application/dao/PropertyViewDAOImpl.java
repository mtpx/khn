package application.dao;

import application.model.views.PropertyView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository("propertyViewDAO")
public class PropertyViewDAOImpl implements PropertyViewDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<PropertyView> findAllForSale() {
        try {
            return em.createNamedQuery(PropertyView.GET_PROPERTIES_FOR_SALE, PropertyView.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropertyView> findByTypeForSale(String propertyType) {
        try {
            return em.createNamedQuery(PropertyView.GET_PROPERTIES_FOR_SALE_BY_TYPE, PropertyView.class)
                    .setParameter("type", propertyType)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<PropertyView> findByEmailForSale(String email) {
        try {
            return em.createNamedQuery(PropertyView.GET_PROPERTIES_FOR_SALE_BY_EMAIL, PropertyView.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<PropertyView> findByEmailSold(String email) {
        try {
            return em.createNamedQuery(PropertyView.GET_SOLD_PROPERTIES_BY_EMAIL, PropertyView.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}

