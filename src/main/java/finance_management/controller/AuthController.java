package finance_management.controller;

import finance_management.dto.user.UserRequest;
import finance_management.dto.user.UserResponse;
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
    public String login(@RequestBody UserRequest userRequest, HttpServletResponse response){
        return authService.verify(userRequest, response);
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest user){
        return authService.registerUser(user);
    }




}
