package application.service;

import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public int login(User user) {
        List<User> result = userDAO.verifyCredentials(user.getEmail(), user.getPassword());
        if(result.size()==0)
            return 0; //user doesnt exists
        return result.get(0).getId();//user exists, returning user id
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String,String> changePasswordDataRequest){
        User myUser = userDAO.getUserByEmail(changePasswordDataRequest.get("email"));
        if(myUser==null)
            return new ResponseEntity<>("User not exists",HttpStatus.BAD_REQUEST);

        String newPassword =changePasswordDataRequest.get("newPassword");
        String oldPasswordFromJson = changePasswordDataRequest.get("oldPassword");
        String oldPasswordFromDb = myUser.getPassword();

        if(oldPasswordFromJson.equals(newPassword))
            return new ResponseEntity<>("New and old passwords should be different",HttpStatus.BAD_REQUEST);
        else if(!oldPasswordFromDb.equals(oldPasswordFromJson))
            return new ResponseEntity<>("Invalid old password", HttpStatus.BAD_REQUEST);
        else {
            userDAO.changePassword(myUser, oldPasswordFromJson, newPassword);
            return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
        }

    }

    @Override
    public Collection getRolesById(int id) {
        return userDAO.getUserRoles(id);
    }


}
