package application.service;

import application.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {
    User addCustomer(User user);
    User addSeller(User user);
    ResponseEntity<Object> deleteUser (int id);
    User findById(int id);
    List<User> findAll();
    int login(User user);
    ResponseEntity<Object> changePassword(Map<String,String> json);
    Collection getRolesById(int id);
}
