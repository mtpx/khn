package application.dao;

import application.model.User;

import java.util.List;

public interface UserDAO {
    User merge(User user);
    List<User> findAll();
}
