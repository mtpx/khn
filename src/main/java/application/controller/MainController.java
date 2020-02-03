package application.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping(value ="/index")
    public String index() {
        return "index";
    }
}
