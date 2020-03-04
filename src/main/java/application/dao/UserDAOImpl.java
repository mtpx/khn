package application.dao;

import application.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            em.remove(user);
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        try {
            return em.createNamedQuery(User.GET_USERS, User.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(int id) {
        try {
            return em.find(User.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> verifyCredentials(String email, String password) {
        try {
            return em.createNamedQuery(User.VERIFY_CREDENTIALS, User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Collection getUserRoles(int id) {
        try {
            return em.createNamedQuery(User.GET_USER_ROLES, Collection.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        try {
            return em.createNamedQuery(User.GET_USER_ID_BY_EMAIL, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public User changePassword(User user, String oldPassword, String newPassword) {
        try {
            user.setPassword(newPassword);
            em.merge(user);
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}
