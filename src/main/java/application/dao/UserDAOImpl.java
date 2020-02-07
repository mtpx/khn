package application.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void merge(Object object) {
        this.em.merge(object);
    }

//    public UserDAOImpl(EntityManager em) {
//        this.em = em;
//    }


}
