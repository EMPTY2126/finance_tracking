package finance_management.controller;

import finance_management.model.User;
import finance_management.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response){
        return authService.verify(user, response);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){



        return authService.registerUser(user);
    }




}
