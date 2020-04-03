package application.service;

import application.model.User;
import application.model.UserChangePassword;
import application.model.UserRegister;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService {
    ResponseEntity<Object> addUser(UserRegister userRegister, String userType);
    ResponseEntity<Object> deleteUser (int id);
    User findById(int id);
    List<User> findAll();
    ResponseEntity<String> changePassword(UserChangePassword userChangePassword);
    ResponseEntity<Object> getUserByEmail(String email);
    ResponseEntity<Object> editUserData(UserRegister userRegister, int id);
    ResponseEntity<Object> addCustomerRoleToSeller(int id);
}
