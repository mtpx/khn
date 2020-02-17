package application.controller;

import application.model.User;
import application.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerAPIController {

    private final UserService userService;

    public CustomerAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/customer/register")
    public User addCustomer(@RequestBody User user){
        return userService.addCustomer(user);
    }

    @PostMapping(value = "/customer/login")
    public int customerLogin(@RequestBody User user){
        return userService.customerLogin(user);
    }

}
