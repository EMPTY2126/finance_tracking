package finance_management.controller;

import finance_management.model.User;
import finance_management.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.verify(user);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){



        return authService.registerUser(user);
    }




}
