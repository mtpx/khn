package application.dao;

import application.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

//    @Transactional(readOnly = false)
//    public User merge(User object) {
//       return em.merge(object);
//    }

    @Transactional(readOnly = false)
    @Override
    public User merge(User object) {
        return em.merge(object);
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery(User.GET_USERS, User.class)
                .getResultList();
    }
//    public UserDAOImpl(EntityManager em) {
//        this.em = em;
//    }


}
