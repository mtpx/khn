package application.service;

import application.dao.UserDAO;
import application.model.User;
import application.dto.UserRegisterDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("userService")

public class UserServiceImpl implements UserService {
    final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserDAO userDAO;
    private final FinanceService financeService;
    private final UserRolesService userRolesService;
    private final UserEditService userEditService;

    public UserServiceImpl(UserDAO userDAO, FinanceService financeService, UserRolesService userRolesService, UserEditService userEditService) {
        this.userDAO = userDAO;
        this.financeService = financeService;
        this.userRolesService = userRolesService;
        this.userEditService = userEditService;
    }

    public ResponseEntity<Object> addUser(UserRegisterDTO userRegisterDTO, String userType) {
        if(userDAO.findByEmail(userRegisterDTO.getEmail())!=null)
            return new ResponseEntity<>("User with provided email exists",HttpStatus.BAD_REQUEST);
        User user = userEditService.prepareUserData(userRegisterDTO, new User());
        switch (userType){
            case "customer":
                user = userDAO.save(userRolesService.addCustomerRole(user)); break;
            case "seller":
                user = userDAO.save(userRolesService.addSellerRole(user)); break;
        }
        financeService.addFinanceRecordToUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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
    public ResponseEntity<Object> getUserByEmail(String email) {
        if(userDAO.findByEmail(email)==null)
            return new ResponseEntity<>("user not exists",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userDAO.findByEmail(email),HttpStatus.OK);
    }

    @Override
    public String getLoggedUserMail() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return null;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return null;

        return context.getAuthentication().getName();
    }

    @Override
    public int getLoggedUserId() {
        return userDAO.findByEmail(getLoggedUserMail()).getId();
    }

    @Override
    public String getLoggedUserFirstName() {
        return userDAO.findByEmail(getLoggedUserMail()).getFirstname();
    }

    @Override
    public String getLoggedUserLastName() {
        return userDAO.findByEmail(getLoggedUserMail()).getLastname();
    }
}
