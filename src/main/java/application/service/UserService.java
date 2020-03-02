package application.service;

import application.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {
    User addCustomer(User user);
    User addSeller(User user);
    User deleteUser (int id);
    User findById(int id);
    List<User> findAll();
    int login(User user);
    boolean changePassword(Map<String,String> json);
    Collection getRolesById(int id);
}
