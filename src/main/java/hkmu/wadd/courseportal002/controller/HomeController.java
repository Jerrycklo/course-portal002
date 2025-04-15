package hkmu.wadd.courseportal002.controller;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.service.CourseService;
import hkmu.wadd.courseportal002.service.PollService;
import hkmu.wadd.courseportal002.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        List<Course> activeCourses = courseService.findByActiveTrue();
        List<Poll> activePolls = pollService.findActivePolls(LocalDateTime.now());
        
        model.addAttribute("courses", activeCourses);
        model.addAttribute("polls", activePolls);
        
        return "home";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Login page requested");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        
        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username is already taken");
            return "register";
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email is already in use");
            return "register";
        }
        
        // Set role to STUDENT by default
        user.setRole(User.Role.STUDENT);
        user.setEnabled(true);
        
        userService.save(user);
        
        return "redirect:/login?registered";
    }
} 