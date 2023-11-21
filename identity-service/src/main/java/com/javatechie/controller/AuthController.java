package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.dto.UserDTO;
import com.javatechie.entity.UserApp;
import com.javatechie.repository.UserRepository;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        String roleName = userDTO.getRoleName();
        UserApp user = new UserApp();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        // Set the profile image only if it's not null
        if (userDTO.getProfileImage() != null) {
            user.setProfileImage(userDTO.getProfileImage());
        }
        return service.saveUser(user, roleName);
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                // Get the authenticated user's details
                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

                // Find the user by username to get the user ID
                Optional<UserApp> userOptional = userRepository.findByName(userDetails.getUsername());

                UserApp user = userOptional.get();

                // Generate the token with the user
                String token = service.generateToken(userDetails.getUsername(), Long.valueOf(user.getId()));

                // Return the token as a JSON object id
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userId", Long.valueOf(user.getId()));

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid credentials"));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Authentication failed: " + e.getMessage()));
        }
    }




    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserApp> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserApp user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            // Map fields from UserApp to UserDTO
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setProfileImage(user.getProfileImage());
            // Map additional fields...

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint";
    }

    @PostMapping("/uploed_image/{id}")
    public ResponseEntity<String> setImageById(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        // Check if the user with the given ID exists
        Optional<UserApp> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserApp user = userOptional.get();

            // Example: Save the image to the database or file system
            // Replace this with your actual logic
            try {
                byte[] imageBytes = file.getBytes();
                user.setProfileImage(imageBytes);
                userRepository.save(user);

                return ResponseEntity.ok("Image set successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set image");
            }
        } else {
            // User with the given ID not found
            return ResponseEntity.notFound().build();
        }
    }




}
