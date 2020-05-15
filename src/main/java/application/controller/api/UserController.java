package application.controller.api;

import application.model.User;
import application.dto.UserChangePasswordDTO;
import application.dto.UserRegisterDTO;
import application.service.UserService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Api(value = "User operations API controller")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Registering seller", response = User.class)
    @PostMapping(value = "/seller/register")
    public ResponseEntity<Object> addSeller(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return userService.addUser(userRegisterDTO, "seller");
    }

    @ApiOperation(value = "Registering customer", response = User.class)
    @PostMapping(value = "/customer/register")
    public ResponseEntity<Object> addCustomer(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return userService.addUser(userRegisterDTO, "customer");
    }

    @ApiOperation(value = "Change password")
    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        return userService.changePassword(userChangePasswordDTO);
    }

    @ApiOperation(value = "Edit user data")
    @ApiImplicitParam(name = "id", value = "User id", required = true, dataType = "int", paramType = "path", defaultValue="1")
    @PostMapping(value = "user/{id}")
    public ResponseEntity<Object> editUserData(@PathVariable int id,@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.editUserData(userRegisterDTO,id);
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
    @GetMapping(value = "/user/email/{email:.+}/")
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
