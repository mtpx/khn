package application.controller;

import application.model.User;
import application.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SellerAPIController {

    final UserService userService;

    public SellerAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/seller/register")
    public User addCustomer(@RequestBody User user){
        return userService.addSeller(user);
    }

    @PostMapping(value = "/seller/login")
    public int customerLogin(@RequestBody User user){
        return userService.sellerLogin(user);
    }


}
