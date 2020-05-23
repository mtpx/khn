package application.service;

import application.dao.UserDAO;
import application.model.Role;
import application.model.User;
import application.model.enums.UserType;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service

public class UserRolesServiceImpl implements UserRolesService {
    final static Logger LOGGER = Logger.getLogger(UserRolesServiceImpl.class.getName());

    private final UserDAO userDAO;

    public UserRolesServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
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

    @Override
    public User addSellerRole(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(UserType.ID_SELLER,UserType.SELLER));
        user.setRoles(roles);
        return user;
    }

    @Override
    public User addCustomerRole(User user) {
        List<Role> roles = new ArrayList<>();
        if(user.getRoles()!=null)   //w przypadku gdy lista ról użytkownika nie jest pusta (przy dodawaniu roli customer dla sprzedawcy)
            roles = user.getRoles();    //uzupełniamy listę rolami aktualnie przypisanymi do użytkownika
        roles.add(new Role(UserType.ID_CUSTOMER,UserType.CUSTOMER));
        user.setRoles(roles);
        return user;
    }
}
