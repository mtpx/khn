package application.dao;
import application.model.Role;
import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Transactional
    @Override
    public User addUser(User user) {
        return em.merge(user);
    }

    @Transactional
    @Override
    public User deleteUser(User user) {
        em.remove(user);
        return user;
    }

    @Override
    public User addCustomerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(em.find(Role.class,3));
        user.setRoles(roles);
        return user;
    }

    @Override
    public User addSellerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(em.find(Role.class,2));
        user.setRoles(roles);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return em.createNamedQuery(User.GET_USERS, User.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(int id) {
        return em.find(User.class,id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> verifyCustomerCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_CUSTOMER_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> verifySellerCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_SELLER_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> verifyCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_SELLER_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    //K: Użytkownik może mieć wiele ról więc trzeba to przerobić na listę :)
    @Transactional(readOnly = true)
    @Override
    public List<User> getUserRoles(int user) {
        return  em.createNamedQuery(User.GET_USER_ROLE, User.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        return em.createNamedQuery(User.GET_USER_ID_BY_EMAIL,User.class)
                .setParameter("email",email)
                .getSingleResult();
    }

    //K: Ja bym zrobiła tutaj update zamiast merge
    @Transactional
    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
            user.setPassword(newPassword);
            em.merge(user);
            return true;
    }

}
