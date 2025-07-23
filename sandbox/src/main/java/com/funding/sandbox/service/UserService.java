package com.funding.sandbox.service;

import com.funding.sandbox.dto.StartupProfileDto;
import com.funding.sandbox.dto.UserRegistrationDto;
import com.funding.sandbox.model.StartupProfile;
import com.funding.sandbox.model.User;
import com.funding.sandbox.model.UserRole;
import com.funding.sandbox.repository.StartupProfileRepository;
import com.funding.sandbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StartupProfileRepository startupProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- FIX 1: Corrected registerUser method ---
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User newUser = new User();
        newUser.setName(registrationDto.getName());
        newUser.setEmail(registrationDto.getEmail());

        // This is the SINGLE place we set the password, and we use the encoder.
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        newUser.setRole(registrationDto.getRole());

        // We only save and return the user ONCE, at the very end.
        return userRepository.save(newUser);
    }

    // --- FIX 2: Corrected createStartupProfile method ---
    public StartupProfile createStartupProfile(StartupProfileDto profileDto) {
        User user = userRepository.findById(profileDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + profileDto.getUserId()));

        // --- FIX 2a: Added a check to ensure only STARTUP roles can create profiles ---
        if (user.getRole() != UserRole.STARTUP) {
            throw new RuntimeException("Only users with the STARTUP role can create a profile.");
        }
        Optional<StartupProfile> existingProfile = startupProfileRepository.findByUser(user);
        if (existingProfile.isPresent()) {
            throw new RuntimeException("This user already has a startup profile.");
        }
        StartupProfile newProfile = new StartupProfile();
        newProfile.setCompanyName(profileDto.getCompanyName());

        // --- FIX 2b: Get business idea from the DTO, not from the empty newProfile object ---
        newProfile.setBusinessIdea(profileDto.getBusineesIdea());

        // --- FIX 2c: Corrected typo 'FundingRequorements' to match your model's field name ---
        newProfile.setFundingRequorements(profileDto.getFundingRequirement());

        newProfile.setUser(user);

        return startupProfileRepository.save(newProfile);
    }

    public List<StartupProfile> getAllStartupProfiles() {
        return startupProfileRepository.findAll();
    }
}