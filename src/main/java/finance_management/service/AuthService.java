package finance_management.service;

import finance_management.dto.user.UserRequest;
import finance_management.dto.user.UserResponse;
import finance_management.exceptions.UserNotFoundException;
import finance_management.mapper.TransactionMapper;
import finance_management.mapper.UserMapper;
import finance_management.model.User;
import finance_management.repo.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(UserRequest userRequest){
        Optional<User> userInDb = userRepo.findByEmail(userRequest.getEmail());
        if(userInDb.isPresent()){
            throw new UserNotFoundException("User Already Exists try another user email", HttpStatus.NOT_FOUND);
        }
//        User dbUser = new User(user.getUserName(), user.getEmail(), passwordEncoder.encode(user.getPassword()));
        User user = UserMapper.toEntity(userRequest);
        user = userRepo.save(user);
//        return new UserResponse(dbUser.getUserName(), dbUser.getEmail(), dbUser.getCreatedAt());
        return UserMapper.toResponse(user);
    }

    public String verify(UserRequest userRequest, HttpServletResponse response){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        if(auth.isAuthenticated()){
            String token = jwtService.generateToken(userRequest.getEmail());

            Cookie cookie = new Cookie("jwt", token);

            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 24);

            response.addCookie(cookie);

            return "Login Successful " + token;
        }

        return "Login Faild";
    }

}
