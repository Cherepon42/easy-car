package by.easycar.service.security;


import by.easycar.exceptions.security.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return true;
        } else {
            throw new WrongPasswordException("Invalid data");
        }
    }

}