package application.service;

import application.model.User;

public interface UserService {
    User addCustomer(User user);
    int customerLogin(String email, String password);
    int sellerLogin(String email, String password);
}
