package application.dao;

import application.model.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    List<User> findAll();
    List<User> verifyCustomerCredentials(String email, String password);
    List<User> verifySellerCredentials(String email, String password);
    int getUserRole(int userId);
    User getUserIdByEmail(String email);
    boolean changePassword(User user, String oldPassword, String newPassword);
}
