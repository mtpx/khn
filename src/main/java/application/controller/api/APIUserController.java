package application.controller.api;

import application.model.User;
import application.model.UserChangePassword;
import application.model.UserRegister;
import application.service.UserService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public ResponseEntity<Object> addSeller(@RequestBody @Valid UserRegister userRegister){
        return userService.addUser(userRegister, "seller");
    }

    @ApiOperation(value = "Registering customer", response = User.class)
    @PostMapping(value = "/customer/register")
    public ResponseEntity<Object> addCustomer(@RequestBody @Valid UserRegister userRegister){
        return userService.addUser(userRegister, "customer");
    }

    @ApiOperation(value = "Change password")
    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePassword userChangePassword) {
        return userService.changePassword(userChangePassword);
    }

    @ApiOperation(value = "Edit user data")
    @ApiImplicitParam(name = "id", value = "User id", required = true, dataType = "int", paramType = "path", defaultValue="1")
    @PostMapping(value = "user/{id}")
    public ResponseEntity<Object> editUserData(@PathVariable int id,@RequestBody UserRegister userRegister) {
        return userService.editUserData(userRegister,id);
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

    @ApiOperation(value = "Delete user")
    @ApiImplicitParam(name = "id", value = "User id", required = true, dataType = "int", paramType = "path", defaultValue="1")
    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Get user by email", response = User.class)
    @ApiImplicitParam(name = "email", value = "User email", required = true, dataType = "String", paramType = "path", defaultValue="email@email.com")
    @GetMapping(value = "/user/email/{email:.+}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @ApiOperation(value = "Get user by email", response = Iterable.class)
    @ApiImplicitParam(name = "email", value = "User email", required = true, dataType = "String", paramType = "path", defaultValue="email@email.com")
    @PostMapping(value = "/user/becomeCustomer/{id}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable int id){
        return userService.addCustomerRoleToSeller(id);
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
