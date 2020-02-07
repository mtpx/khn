package application.controller;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping(value = "/customer", consumes = APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody User user){
        userDAO.merge(user);
    }
}
