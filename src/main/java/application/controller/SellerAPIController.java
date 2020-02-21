package application.controller;

import application.model.User;
import application.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class SellerAPIController {

    final UserService userService;

    public SellerAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/seller/register")
    public User addCustomer(@RequestBody @Valid User user){
        return userService.addSeller(user);
    }
}
