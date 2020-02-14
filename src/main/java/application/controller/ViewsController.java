package application.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewsController {

    @GetMapping(value ="/index")
    public String index() {
        return "index";
    }

    @GetMapping(value ="/changePassword")
    public String changePassword() {
        return "changePassword";
    }




    @GetMapping(value ="/admin/index")
    public String adminIndex() {
        return "admin/index";
    }




    @GetMapping(value ="/seller/register")
    public String sellerRegister() {
        return "seller/register";
    }

    @GetMapping(value ="/seller/login")
    public String sellerLogin() {
        return "seller/login";
    }

    @GetMapping(value ="/seller/index")
    public String sellerIndex() {
        return "seller/index";
    }




    @GetMapping(value ="/customer/register")
    public String customerRegister() {
        return "customer/register";
    }

    @GetMapping(value ="/customer/login")
    public String customerLogin() {
        return "customer/login";
    }

    @GetMapping(value ="/customer/index")
    public String customerIndex() {
        return "customer/index";
    }
}
