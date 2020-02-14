package application.controller;

import application.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class SellerAPIController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/seller/login", consumes = APPLICATION_JSON_VALUE)
    public int checkUser(@RequestBody ObjectNode objectNode){
        String email = objectNode.get("email").asText();
        String password = objectNode.get("password").asText();
        return userService.sellerLogin(email, password);
    }
}
