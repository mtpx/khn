package application.controller;

        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerMainController {

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
