package application.controller;

import application.model.User;
import application.service.UserService;
import application.service.UserServiceImpl;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public boolean changePassword(@RequestBody ChangePasswordData changePasswordData){
        return userService.changePassword(changePasswordData);
    }

    @PostMapping(value = "/login")
    public UserServiceImpl.UserData login(@RequestBody User user){
        return userService.login(user);
    }

    //K: Odnośnie pytania na slacku o HTTP to poczytaj sobie o zwracaniu ResponseEntity - Jak coś by nie szło to pomogę :)

    @GetMapping(value = "/user")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable int id){
        return userService.findById(id);
    }

    @DeleteMapping(value = "/user/{id}")
    public User deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }


}
