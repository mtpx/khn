package application.dao;

import application.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO {
    User merge(User object);
    List<User> findAll();
}
