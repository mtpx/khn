package application.dao;

import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional(readOnly = false)
    @Override
    public User addCustomer(User user) {
        return em.merge(user);
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery(User.GET_USERS, User.class)
                .getResultList();
    }

    @Override
    public List<User> verifyCustomerCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_CUSTOMER_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    @Override
    public List<User> verifySellerCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_SELLER_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    @Override
    public User getUserRole(int user) {
        return em.createNamedQuery(User.GET_USER_ROLE, User.class)
                .setParameter("user", user)
                .getSingleResult();
    }

}
