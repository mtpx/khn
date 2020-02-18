package application.service;

import application.controller.CommonAPIController;
import application.model.User;

import java.util.List;

public interface UserService {
    User addCustomer(User user);
    User addSeller(User user);
    User deleteUser (int id);
    List<User> findAll();
    User findById(int id);
    int customerLogin(User user);
    UserServiceImpl.UserData login(User user);
    int sellerLogin(User user);
    boolean changePassword(CommonAPIController.ChangePasswordData changePasswordData);
}
