package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SellerMainController {

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

}
