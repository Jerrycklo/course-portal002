package hkmu.wadd.courseportal002.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Application is working correctly!";
    }
    
    @GetMapping("/test-login")
    public String testLogin() {
        return "Login test endpoint working!";
    }
    
    @GetMapping("/test-home")
    public String testHome() {
        return "Home test endpoint working!";
    }
} 