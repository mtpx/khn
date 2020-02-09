package application.controller;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void addUser(@RequestBody User user){
        userDAO.merge(user);
    }
}
