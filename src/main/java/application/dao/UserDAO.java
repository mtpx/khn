package application.dao;

import application.model.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {
    User addUser(User user);
    User deleteUser(User user);
    User addCustomerRole(User user);
    User addSellerRole(User user);
    List<User> findAll();
    User findUserById(int id);
    List<User> verifyCredentials(String email, String password);
    Collection getUserRoles(int userId);
    User getUserByEmail(String email);
    boolean changePassword(User user, String oldPassword, String newPassword);
}
