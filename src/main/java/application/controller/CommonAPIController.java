package application.controller;

import application.model.User;
import application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class CommonAPIController {

    private UserService userService;

    public CommonAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> changePasswordDataRequest){
        return userService.changePassword(changePasswordDataRequest);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody User user){
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

    @GetMapping(value = "/user/role/email/{email}")
    public Collection getRolesByEmail(@PathVariable String email){
        return userService.getRolesByEmail(email);
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @GetMapping(value = "/user/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }


    //spring security

    @GetMapping(value = "/user/getSecuredUserName")
    public String getSecuredUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping(value = "/user/getSecuredUserAuthorities")
    public Collection<? extends GrantedAuthority> getSecuredUserRoles(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities();
    }

    @GetMapping(value = "/user/getSecuredUserPrincipal")
    public Object getSecuredUserPrincipal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }


}
