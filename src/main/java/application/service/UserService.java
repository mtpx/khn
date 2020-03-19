package application.service;

import application.model.User;
import application.model.UserChangePassword;
import org.springframework.http.ResponseEntity;
import java.util.Collection;
import java.util.List;

public interface UserService {
    User addCustomer(User user);
    User addSeller(User user);
    ResponseEntity<Object> deleteUser (int id);
    User findById(int id);
    List<User> findAll();
    ResponseEntity<String> changePassword(UserChangePassword userChangePassword);
    User getUserByEmail(String email);
}
