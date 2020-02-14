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
    public User addUser(User user) {
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
    public int getUserRole(int user) {
        User myUser= em.createNamedQuery(User.GET_USER_ROLE, User.class)
                .setParameter("user", user)
                .getSingleResult();
        return myUser.getRoles().id;
    }

    @Override
    public User getUserIdByEmail(String email) {
        User myUser = em.createNamedQuery(User.GET_USER_ID_BY_EMAIL,User.class)
                .setParameter("email",email)
                .getSingleResult();
        return myUser;
    }

    @Transactional(readOnly = false)
    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if(user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            em.merge(user);
            return true;
        }
        else
            return false;
    }

}
