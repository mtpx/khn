package application.service;

import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import application.dto.UserChangePasswordDTO;
import application.dto.UserRegisterDTO;
import application.model.enums.UserType;
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

    public ResponseEntity<Object> addUser(UserRegisterDTO userRegisterDTO, String userType) {
        if(userDAO.findByEmail(userRegisterDTO.getEmail())!=null)
            return new ResponseEntity<>("User with provided email exists",HttpStatus.BAD_REQUEST);
        User user = new User();
        user.setFirstname(userRegisterDTO.getFirstname());
        user.setLastname(userRegisterDTO.getLastname());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        switch (userType){
            case "customer":
                return new ResponseEntity<>(userDAO.save(addCustomerRole(user)), HttpStatus.CREATED);
            case "seller":
                return new ResponseEntity<>(userDAO.save(addSellerRole(user)), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Wrong user type", HttpStatus.BAD_REQUEST);
    }

    private User addSellerRole(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(UserType.ID_SELLER,UserType.SELLER));
        user.setRoles(roles);
        return user;
    }

    private User addCustomerRole(User user) {
        List<Role> roles = new ArrayList<>();;
        if(user.getRoles()!=null)   //w przypadku gdy lista ról użytkownika nie jest pusta (przy dodawaniu roli customer dla sprzedawcy)
            roles = user.getRoles();    //uzupełniamy listę rolami aktualnie przypisanymi do użytkownika
        roles.add(new Role(UserType.ID_CUSTOMER,UserType.CUSTOMER));
        user.setRoles(roles);
        return user;
    }

    @Override
    public ResponseEntity<Object> deleteUser(int id) {
        User deletedUser = userDAO.findById(id);
        if(deletedUser==null)
            return new ResponseEntity<>("User doesnt exists", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userDAO.delete(deletedUser), HttpStatus.OK);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public ResponseEntity<String> changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        User myUser = userDAO.findByEmail(userChangePasswordDTO.getEmail());
        if (myUser == null)
            return new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);

        String oldPasswordFromDb = myUser.getPassword();

        if (userChangePasswordDTO.getOldPassword().equals(userChangePasswordDTO.getNewPassword())){
            return new ResponseEntity<>("New and old passwords should be different", HttpStatus.BAD_REQUEST);
        }else if (!oldPasswordFromDb.equals(userChangePasswordDTO.getOldPassword())){
            return new ResponseEntity<>("Invalid old password", HttpStatus.BAD_REQUEST);
        }else {
            userDAO.changePassword(myUser, userChangePasswordDTO.getOldPassword(), userChangePasswordDTO.getNewPassword());
            return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
        }

    }


    @Override
    public ResponseEntity<Object> getUserByEmail(String email) {
        if(userDAO.findByEmail(email)==null)
            return new ResponseEntity<>("user not exists",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userDAO.findByEmail(email),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> editUserData(UserRegisterDTO userRegisterDTO, int id) {
        if(userDAO.findByEmail(userRegisterDTO.getEmail())!=null && !userDAO.findById(id).getEmail().equals(userRegisterDTO.getEmail()))
            return new ResponseEntity<>("User with following email exists",HttpStatus.BAD_REQUEST);
        User user = userDAO.findById(id);
        user.setFirstname(userRegisterDTO.getFirstname());
        user.setLastname(userRegisterDTO.getLastname());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        userDAO.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCustomerRoleToSeller(int id) {
        User user = userDAO.findById(id);
        if (user.getRoles().size()==1) {    //możemy dodać customera jedynie jeśli użytkownik ma jedną rolę 'seller'
            if (user.getRoles().get(0).getName().equals("seller"))
                return new ResponseEntity<>(userDAO.save(addCustomerRole(user)), HttpStatus.OK);
        }
        return new ResponseEntity<>("Adding customer role error: user has customer role or cannot have seller role", HttpStatus.BAD_REQUEST);
    }


}
