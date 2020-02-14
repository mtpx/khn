package application.controller;

import application.dao.UserDAO;
import application.model.User;
import application.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CommonAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @PostMapping(value = "/register")
    public User addCustomer(@RequestBody User user){
        return userService.addCustomer(user);
    }

    @PostMapping(value = "/changePassword")
    public boolean changePassword(@RequestBody ObjectNode objectNode){
        String email = objectNode.get("email").asText();
        String oldPassword = objectNode.get("oldPassword").asText();
        String newPassword = objectNode.get("newPassword").asText();
        return userService.changePassword(email,oldPassword,newPassword);
    }

    @GetMapping(value = "/users")
    public List<User> getUser(){
        return userDAO.findAll();
    }

}
