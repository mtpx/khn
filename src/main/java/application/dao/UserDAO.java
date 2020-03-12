package application.dao;

import application.model.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {
    User addUser(User user);
    User deleteUser(User user);
    List<User> findAll();
    User findUserById(int id);
    List<User> verifyCredentials(String email, String password);
    Collection getUserRoles(int userId);
    Collection getUserRolesByEmail(String email);
    User getUserByEmail(String email);
    User changePassword(User user, String oldPassword, String newPassword);
}
