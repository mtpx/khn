package application.controller;

import application.dao.UserDAO;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @PostMapping(value = "/customer")
    public User addCustomer(@RequestBody User user){
        return userService.addCustomer(user);
    }

    @GetMapping(value = "/customer")
    public List<User> getUser(){
        return userDAO.findAll();
    }
}
