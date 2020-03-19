package application.service;

import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import application.model.UserChangePassword;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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


    private User addSellerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(new Role(2,"seller"));
        user.setRoles(roles);
        return user;
    }

    private User addCustomerRole(User user) {
        List<Role> roles= new ArrayList<>();
        roles.add(new Role(3,"customer"));
        user.setRoles(roles);
        return user;
    }

    @Override
    public ResponseEntity<Object> deleteUser(int id) {
        User deletedUser = userDAO.findUserById(id);
        if(deletedUser==null)
            return new ResponseEntity<>("User doesnt exists", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userDAO.deleteUser(deletedUser), HttpStatus.OK);
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
    public ResponseEntity<String> changePassword(UserChangePassword userChangePassword) {
        User myUser = userDAO.getUserByEmail(userChangePassword.getEmail());
        if (myUser == null)
            return new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);

        String oldPasswordFromDb = myUser.getPassword();

        if (userChangePassword.getOldPassword().equals(userChangePassword.getNewPassword())){
            return new ResponseEntity<>("New and old passwords should be different", HttpStatus.BAD_REQUEST);
        }else if (!oldPasswordFromDb.equals(userChangePassword.getOldPassword())){
            return new ResponseEntity<>("Invalid old password", HttpStatus.BAD_REQUEST);
        }else {
            userDAO.changePassword(myUser, userChangePassword.getOldPassword(), userChangePassword.getNewPassword());
            return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
        }

    }


    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }


}
