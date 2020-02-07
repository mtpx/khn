package application.dao;

import application.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO {
    void merge(Object object);
}
