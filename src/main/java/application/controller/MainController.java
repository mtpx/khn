package application.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @GetMapping(value ="/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value ="/index")
    public String index() {
        return "index";
    }
}
