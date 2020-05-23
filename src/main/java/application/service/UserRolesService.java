package application.service;

import application.model.User;
import org.springframework.http.ResponseEntity;

public interface UserRolesService {
    ResponseEntity<Object> addCustomerRoleToSeller(int id);
    User addSellerRole(User user);
    User addCustomerRole(User user);
}
