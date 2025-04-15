package hkmu.wadd.courseportal002.controller;

import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;

@Controller
public class CustomLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/custom-login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/custom-login")
    public String processLogin(@RequestParam String username, 
                              @RequestParam String password, 
                              Model model) {
        
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Manual authentication
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    username, 
                    password, 
                    Collections.singleton(authority)
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                return "redirect:/";
            }
        }
        
        // Login failed
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
} 