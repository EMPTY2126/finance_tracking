package finance_management.service;

import finance_management.model.User;
import finance_management.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User registerUser(User user){

    }

}
