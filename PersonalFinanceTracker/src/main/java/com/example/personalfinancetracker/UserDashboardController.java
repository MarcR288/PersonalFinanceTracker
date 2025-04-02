package com.example.personalfinancetracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDashboardController {

    @GetMapping("/home")
    public String homepage() {
        return "Home";
    }
}
