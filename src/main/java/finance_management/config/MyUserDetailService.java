package finance_management.config;

import finance_management.repo.UserRepo;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("User Not found")
                );
    }

}
