package finance_management.config;

import finance_management.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, MyUserDetailService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = null;

        if(cookies == null){
            filterChain.doFilter(request,response);
            return;
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("jwt")){
                jwtCookie = cookie;
                break;
            }
        }

        if(jwtCookie == null){
            filterChain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtCookie.getValue();
        String userEmail = jwtService.extractUserEmail(jwtToken);
        UserDetails userDetail = userDetailsService.loadUserByUsername(userEmail);
        boolean isValidToken =  jwtService.isTokenValid(jwtToken, userDetail);
        if(!isValidToken){
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetail,
                null,
                userDetail.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);
    }
}
