package application.dao;

import application.model.User;

import java.util.List;

public interface UserDAO {
    User addCustomer(User user);
    List<User> findAll();
    List<User> verifyCustomerCredentials(String email, String password);
    List<User> verifySellerCredentials(String email, String password);
    User getUserRole(int userId);
}
