package application.service;

import application.controller.CommonAPIController;
import application.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User addCustomerRole(User user);
    User addSellerRole(User user);
    User addCustomer(User user);
    User addSeller(User user);
    User deleteUser (int id);
    List<User> findAll();
    User findById(int id);
    int login(User user);
    boolean changePassword(CommonAPIController.ChangePasswordData changePasswordData);
    Collection getRolesById(int id);
}
