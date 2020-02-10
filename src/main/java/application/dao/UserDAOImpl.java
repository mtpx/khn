package application.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(type= PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void merge(Object object) {
        em.merge(object);
    }


//    public UserDAOImpl(EntityManager em) {
//        this.em = em;
//    }


}
