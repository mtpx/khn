package application.service;

import application.model.User;
import application.dto.UserChangePasswordDTO;
import application.dto.UserRegisterDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService {
    ResponseEntity<Object> addUser(UserRegisterDTO userRegisterDTO, String userType);
    ResponseEntity<Object> deleteUser (int id);
    User findById(int id);
    List<User> findAll();
    ResponseEntity<Object> getUserByEmail(String email);
    String getLoggedUser();
    int getLoggedUserId();

}
