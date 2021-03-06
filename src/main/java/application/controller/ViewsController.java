package application.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    @GetMapping(value ="/index")
    public String index() {
        return "/index.html";
    }

    @GetMapping(value ="/changePassword")
    public String changePassword() {
        return "/changePassword.html";
    }

    @GetMapping(value ="/main")
    public String main(Model model) {
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "/main.jsp";
    }

    @GetMapping(value ="/footer")
    public String footer() {
        return "/footer.html";
    }

    @GetMapping(value ="/navigationMenu")
    public String navigationMenu() {
        return "/navigationMenu.html";
    }

    @GetMapping(value ="/auctions")
    public String auctions() {
        return "/auctions.html";
    }

    @GetMapping(value ="/profile")
    public String sellerProfile(Model model) {
        model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/profile.jsp";
    }




    @GetMapping(value ="/admin/users")
    public String adminUsers() {
        return "admin/users.html";
    }

    @GetMapping(value ="/admin/users2")
    public String adminUsers2() {
        return "admin/users2.html";
    }




    @GetMapping(value ="/seller/register")
    public String sellerRegister() {
        return "seller/register.html";
    }

    @GetMapping(value ="/seller/login")
    public String sellerLogin() {
        return "seller/login.html";
    }

//    @GetMapping(value ="/seller/sales")
//    public String sellerSales() {
//        return "seller/sales.xhtml";
//    }

    @GetMapping(value ="/seller/addProperty")
    public String addProperty() {
        return "seller/addProperty.html";
    }




    @GetMapping(value ="/customer/register")
    public String customerRegister() {
        return "customer/register.html";
    }

//    @GetMapping(value ="/customer/credits")
//    public String credits() {
//        return "customer/credits.xhtml";
//    }

    @GetMapping(value ="/customer/credits2")
    public String creditsjs() {
        return "customer/creditsjs.html";
    }

    @GetMapping(value ="/customer/login")
    public String customerLogin() {
        return "customer/login.html";
    }

//    @GetMapping(value ="/customer/buyings")
//    public String buyings() {
//        return "customer/buyings.xhtml";
//    }
}
