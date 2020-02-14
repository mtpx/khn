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
        return userDAO.addUser(user);
    }

    @Override
    public int customerLogin(String email, String password) {
        List<User> result = userDAO.verifyCustomerCredentials(email, password);
        if(result.size()!=0) {
            return result.get(0).id ; //user exists
        }else {
            return 0; //user doesnt exist
        }
    }

    @Override
    public int sellerLogin(String email, String password) {
        List<User> result = userDAO.verifySellerCredentials(email, password);
        if(result.size()!=0) {
            return result.get(0).id ;
        }else {
            return 0;
        }
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword){
        User myUser = userDAO.getUserIdByEmail(email);
        return userDAO.changePassword(myUser, oldPassword,newPassword);
    }

}
