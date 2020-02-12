package application.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewsController {

    @GetMapping(value ="/index")
    public String index() {
        return "index";
    }




    @GetMapping(value ="/seller/register")
    public String sellerRegister() {
        return "seller/register";
    }

    @GetMapping(value ="/seller/login")
    public String sellerLogin() {
        return "seller/login";
    }

    @GetMapping(value ="/seller/changePassword")
    public String sellerChangePassword() {
        return "seller/changePassword";
    }




    @GetMapping(value ="/customer/register")
    public String customerRegister() {
        return "customer/register";
    }

    @GetMapping(value ="/customer/login")
    public String customerLogin() {
        return "customer/login";
    }

    @GetMapping(value ="/customer/changePassword")
    public String customerChangePassword() {
        return "customer/changePassword";
    }
}
