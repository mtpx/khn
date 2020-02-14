package application.controller;

import application.dao.UserDAO;
import application.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class CustomerAPIController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/customer/login", consumes = APPLICATION_JSON_VALUE)
    public int checkUser(@RequestBody ObjectNode objectNode){
        String email = objectNode.get("email").asText();
        String password = objectNode.get("password").asText();
        return userService.customerLogin(email, password);
    }


}
