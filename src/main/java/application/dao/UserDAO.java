package application.dao;

import application.model.User;
import java.util.List;

public interface UserDAO {
    User save(User user);
    User delete(User user);
    List<User> findAll();
    User findById(int id);
    User findByEmail(String email);
    User changePassword(User user, String oldPassword, String newPassword);
}
