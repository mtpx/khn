package application.dao;

import application.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    User deleteUser(User user);
    User addCustomerRole(User user);
    User addSellerRole(User user);
    List<User> findAll();
    User findUserById(int id);
    List<User> verifyCustomerCredentials(String email, String password);
    List<User> verifySellerCredentials(String email, String password);
    int getUserRole(int userId);
    User getUserByEmail(String email);
    boolean changePassword(User user, String oldPassword, String newPassword);
}
