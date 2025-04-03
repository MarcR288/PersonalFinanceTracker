package com.example.personalfinancetracker.Controller;

import com.example.personalfinancetracker.model.User;
import com.example.personalfinancetracker.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Login Form
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // Registration Form
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    // Authenticate login
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/dashboard";
    }

    // Logs out a user
    @PostMapping("/logout")
    public String logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }

    // Saves a new user to the db
    @PostMapping("/register")
    public String registerAccount(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String confirmPassword,
                                  RedirectAttributes redirectAttributes) {
        // Check if the username is already taken
        if (userService.findByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute("usernameError", "Username is already taken");
            System.out.println("Username is already taken: " + username);
            return "redirect:/register"; // Make sure to redirect back to the register page
        }

        // Check if passwords match
        if (password.equals(confirmPassword)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            User user = new User(username, encodedPassword);
            userService.saveUser(user);
            System.out.println("User saved: " + username + " " + password);
            return "redirect:/login"; // Redirect to login after successful registration
        }
        return "redirect:/register";
    }

}
