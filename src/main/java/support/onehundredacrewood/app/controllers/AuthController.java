package support.onehundredacrewood.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }
}
