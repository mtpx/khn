package application.dao;
import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.Collection;
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
    public List<User> verifyCredentials(String email, String password) {
        return em.createNamedQuery(User.VERIFY_CREDENTIALS, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection getUserRoles(int id) {
        return  em.createNamedQuery(User.GET_USER_ROLE, Collection.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        return em.createNamedQuery(User.GET_USER_ID_BY_EMAIL,User.class)
                .setParameter("email",email)
                .getSingleResult();
    }

    @Transactional
    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
            user.setPassword(newPassword);
            em.merge(user);
            return true;
    }

}
