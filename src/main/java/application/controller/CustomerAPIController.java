package application.controller;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerAPIController {

    private UserDAO userDAO;

    public CustomerAPIController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


//    @GetMapping(value = "/user")
//    public Iterable<User> getUsersQuery(){
//        return userDAO.findAll();
//    }

    @PostMapping(value = "/customer")
    public User addUser(@RequestBody User user){
       return userDAO.merge(user);
    }

    @GetMapping(value = "/customers")
    public List<User> getUser(){
        return userDAO.findAll();
    }
}
