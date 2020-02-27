package application.service;

import application.controller.CommonAPIController;
import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
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
        return userDAO.addUser(addCustomerRole(user));
    }

    @Override
    public User addSeller(User user) {
        return userDAO.addUser(addSellerRole(user));
    }

    @Override
    public User addSellerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(new Role(2,"seller"));
        user.setRoles(roles);
        return user;
    }

    @Override
    public User addCustomerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(new Role(3,"customer"));
        user.setRoles(roles);
        return user;
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
    public int login(User user) {
        List<User> result = userDAO.verifyCredentials(user.getEmail(), user.getPassword());
        if(result.size()==0)
            return 0; //user doesnt exists
        return result.get(0).getId();//user exists, returning user id
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

    @Override
    public Collection getRolesById(int id) {
        return userDAO.getUserRoles(id);
    }


}
