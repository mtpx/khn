package application.dao;

import application.model.User;

import java.util.List;

public interface UserDAO {
    User addCustomer(User user);
    List<User> findAll();
}
