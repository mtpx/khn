package application.service;

import application.controller.CommonAPIController;
import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());


    private final UserDAO userDAO;
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User addCustomer(User user) {
        LOGGER.info("user user");
        return userDAO.addCustomerRole(userDAO.addUser(user));
    }

    @Override
    public User addSeller(User user) {
        return userDAO.addSellerRole(userDAO.addUser(user));
    }

    @Override
    public User deleteUser(int id) {
        User deletedUser = userDAO.findUserById(id);
        return userDAO.deleteUser(deletedUser);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(int id) {
        return userDAO.findUserById(id);
    }

    @Override
    public int customerLogin(User user) {
        List<User> result = userDAO.verifyCustomerCredentials(user.getEmail(), user.getPassword());
        if(result.size()!=0) {
            return result.get(0).id; //user exists, returning user id
        }else {
            return 0; //user doesnt exist
        }
    }

    @Override
    public int sellerLogin(User user) {
        List<User> result = userDAO.verifySellerCredentials(user.getEmail(), user.getPassword());
        if(result.size()!=0) {
            return result.get(0).id; //user exists, returning user id
        }else {
            return 0; //user doesnt exist
        }
    }

    @Override
    public UserData login(User user) {
        UserData userData = new UserData();
        List<User> result = userDAO.verifyCredentials(user.getEmail(), user.getPassword());
        if(result.size()==0) {
            userData.setUserId(0);
            return userData;
        }
        userData.setUserId(result.get(0).getId());
        userData.setRoleId(result.get(0).getRoles());
        return userData;//user exists, returning user id
    }

    @Override
    public boolean changePassword(CommonAPIController.ChangePasswordData changePasswordData){
        User myUser = userDAO.getUserByEmail(changePasswordData.getEmail());
        String oldPassword = myUser.getPassword();
        if(oldPassword.equals(changePasswordData.getOldPassword()))
            return userDAO.changePassword(myUser, changePasswordData.getOldPassword(), changePasswordData.getNewPassword());
        else
            return false;
    }

    @Data
    public static class UserData {
        private int userId;
        private List<Role> roleId;
    }
}
