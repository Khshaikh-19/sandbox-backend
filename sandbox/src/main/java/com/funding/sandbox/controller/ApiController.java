package com.funding.sandbox.controller;
import com.funding.sandbox.dto.StartupProfileDto;
import com.funding.sandbox.dto.UserRegistrationDto;
import com.funding.sandbox.model.StartupProfile;
import com.funding.sandbox.model.User;
import com.funding.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api")

public class ApiController {
    @Autowired

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User registeredUser = userService.registerUser(registrationDto);
        return ResponseEntity.ok(registeredUser);
    }
    @PostMapping("/startups/profile")
    public ResponseEntity<StartupProfile>createdStartupProfile(@RequestBody StartupProfileDto profileDto){

        StartupProfile createdProfile=userService.createStartupProfile(profileDto);
        return ResponseEntity.ok(createdProfile);
    }

    @GetMapping("/startups")
    public ResponseEntity<List<StartupProfile>> getAllStartups() {
        List<StartupProfile> profiles = userService.getAllStartupProfiles();
        return ResponseEntity.ok(profiles);
    }

}
