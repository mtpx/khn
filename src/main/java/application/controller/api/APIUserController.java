package application.controller.api;

import application.model.User;
import application.model.UserChangePassword;
import application.service.UserService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Api(value = "KHN user API controller")
@RestController
public class APIUserController {

    private UserService userService;

    public APIUserController(UserService userService) {
        this.userService = userService;
    }



    @ApiOperation(value = "Registering seller", response = User.class)
    @PostMapping(value = "/seller/register")
    public User addSeller(@RequestBody @Valid User user){
        return userService.addSeller(user);
    }

    @ApiOperation(value = "Registering customer", response = User.class)
    @PostMapping(value = "/customer/register")
    public User addCustomer(@RequestBody @Valid User user){
        return userService.addCustomer(user);
    }

    @ApiOperation(value = "Change password")
    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePassword userChangePassword) {
        return userService.changePassword(userChangePassword);
    }

    @ApiOperation(value = "Get all users", response = User.class)
    @GetMapping(value = "/user")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @ApiOperation(value = "Get user by id", response = User.class)
    @ApiImplicitParam(name = "id", value = "User id", required = true, dataType = "int", paramType = "path", defaultValue="1")
    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable int id){
        return userService.findById(id);
    }

    @ApiOperation(value = "Get user data by id")
    @ApiImplicitParam(name = "id", value = "User id", required = true, dataType = "int", paramType = "path", defaultValue="1")
    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Get user data by email", response = Iterable.class)
    @ApiImplicitParam(name = "email", value = "User email", required = true, dataType = "String", paramType = "path", defaultValue="email@email.com")
    @GetMapping(value = "/user/email/{email:.+}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }


    //spring security

//    @GetMapping(value = "/user/getSecuredUserName")
//    public String getSecuredUserName(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getName();
//    }
//
//    @GetMapping(value = "/user/getSecuredUserAuthorities")
//    public Collection<? extends GrantedAuthority> getSecuredUserRoles(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getAuthorities();
//    }
//
//    @GetMapping(value = "/user/getSecuredUserPrincipal")
//    public Object getSecuredUserPrincipal(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getPrincipal();
//    }


}
