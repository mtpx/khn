package application.service;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User addCustomer(User user) {
        return userDAO.addCustomer(user);
    }

    @Override
    public int customerLogin(String email, String password) {
        List<User> result = userDAO.verifyCustomerCredentials(email, password);
        if(result.size()!=0) {
            return result.get(0).id ; //logowanie udane
        }else {
            return 0; //logowanie nieudane
        }
    }

    @Override
    public int sellerLogin(String email, String password) {
        List<User> result = userDAO.verifySellerCredentials(email, password);
        if(result.size()!=0) {
            return result.get(0).id ; //logowanie udane
        }else {
            return 0; //logowanie nieudane
        }
    }
}
