package application.controller;

import application.model.User;
import application.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class CustomerAPIController {

    private final UserService userService;

    public CustomerAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/customer/register")
    public User addCustomer(@RequestBody @Valid User user){
            return userService.addCustomer(user);
    }

}
