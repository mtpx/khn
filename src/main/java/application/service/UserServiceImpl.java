package application.service;

import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import application.model.UserChangePassword;
import application.model.UserRegister;
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

    public ResponseEntity<Object> addUser(UserRegister userRegister, String userType) {
        if(userDAO.getUserByEmail(userRegister.getEmail())!=null)
            return new ResponseEntity<>("User with provided email exists",HttpStatus.BAD_REQUEST);
        User user = new User();
        user.setFirstname(userRegister.getFirstname());
        user.setLastname(userRegister.getLastname());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        switch (userType){
            case "customer":
                return new ResponseEntity<>(userDAO.addUser(addCustomerRole(user)), HttpStatus.CREATED);
            case "seller":
                return new ResponseEntity<>(userDAO.addUser(addSellerRole(user)), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Wrong user type", HttpStatus.BAD_REQUEST);
    }

    private User addSellerRole(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2,"seller"));
        user.setRoles(roles);
        return user;
    }

    private User addCustomerRole(User user) {
        List<Role> roles = new ArrayList<>();;
        if(user.getRoles()!=null)   //w przypadku gdy lista ról użytkownika nie jest pusta (przy dodawaniu roli customer dla sprzedawcy)
            roles = user.getRoles();    //uzupełniamy listę rolami aktualnie przypisanymi do użytkownika
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
    public ResponseEntity<Object> getUserByEmail(String email) {
        if(userDAO.getUserByEmail(email)==null)
            return new ResponseEntity<>("user not exists",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userDAO.getUserByEmail(email),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> editUserData(UserRegister userRegister, int id) {
        if(userDAO.getUserByEmail(userRegister.getEmail())!=null && !userDAO.findUserById(id).getEmail().equals(userRegister.getEmail()))
            return new ResponseEntity<>("User with following email exists",HttpStatus.BAD_REQUEST);
        User user = userDAO.findUserById(id);
        user.setFirstname(userRegister.getFirstname());
        user.setLastname(userRegister.getLastname());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        userDAO.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCustomerRoleToSeller(int id) {
        User user = userDAO.findUserById(id);
        if (user.getRoles().size()==1) {    //możemy dodać customera jedynie jeśli użytkownik ma jedną rolę 'seller'
            if (user.getRoles().get(0).getName().equals("seller"))
                return new ResponseEntity<>(userDAO.addUser(addCustomerRole(user)), HttpStatus.OK);
        }
        return new ResponseEntity<>("Adding customer role error: user has customer role or cannot have seller role", HttpStatus.BAD_REQUEST);
    }


}
