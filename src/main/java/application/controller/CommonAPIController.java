package application.controller;

import application.model.User;
import application.service.UserService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class CommonAPIController {

    @Getter
    public static class ChangePasswordData {
        String email;
        String oldPassword;
        String newPassword;
    }

    private UserService userService;

    public CommonAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/changePassword")
    public boolean changePassword(@RequestBody Map<String,String> changePasswordDataRequest){
        return userService.changePassword(changePasswordDataRequest);
    }

    @PostMapping(value = "/login")
    public int login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping(value = "/user")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable int id){
        return userService.findById(id);
    }

    @GetMapping(value = "/user/role/{id}")
    public Collection getRoles(@PathVariable int id){
        return userService.getRolesById(id);
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }


}
