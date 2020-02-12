package application.controller;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerAPIController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping(value = "/customer")
    public List<User> getUser(){
        return userDAO.findAll();
    }
}
