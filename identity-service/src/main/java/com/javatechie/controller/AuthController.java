package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.dto.UserDTO;
import com.javatechie.entity.Role;
import com.javatechie.entity.UserApp;
import com.javatechie.repository.RoleRepository;
import com.javatechie.repository.UserRepository;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RefreshScope
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/count")
    public long countAllUsers() {
        return userRepository.count();
    }
    @GetMapping("/countByRole")
    public Map<String, Long> countUsersByRole() {
        List<UserApp> users = userRepository.findAll();

        Map<String, Long> countByRole = new HashMap<>();
        for (UserApp user : users) {
            String role = String.valueOf(user.getRole());
            // Otherwise, increment the count for that role
            countByRole.put(role, countByRole.getOrDefault(role, 0L) + 1);
        }
        return countByRole;
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        String roleName = userDTO.getRoleName();
        UserApp user = new UserApp();
        user.setId(userDTO.getId());
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
                response.put("userId", user.getId()); // Assuming user.getId() is already a Long
                response.put("role", user.getRole());

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint";
    }

        @PostMapping("/upload_image/{id}")
        public ResponseEntity<String> setImageById(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
            // Check if the user with the given ID exists
            Optional<UserApp> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                UserApp user = userOptional.get();

                try {
                    byte[] imageBytes = file.getBytes();
                    user.setProfileImage(imageBytes);

                    // Save the updated user to the database
                    userRepository.save(user);

                    return ResponseEntity.ok("Image set successfully");
                } catch (IOException e) {
                    e.printStackTrace(); // Add this line to log the exception details
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set image");
                }

            } else {
                // User with the given ID not found
                return ResponseEntity.notFound().build();
            }
        }

    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable Long id) {
        Optional<UserApp> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserApp updatedUser) {
        Optional<UserApp> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserApp user = userOptional.get();

            // Update user information based on the fields you want to allow the user to update
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            // Check if the password is provided before updating
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Encrypt the password
            }

       // Update profile image
            userRepository.save(user);

            return ResponseEntity.ok("User information updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/drivers")
    public List<UserApp> getDrivers() {
        Role driverRole = roleRepository.findByName("DRIVER");

// Finding users by the retrieved role object
        List<UserApp> drivers = userRepository.findByRole(driverRole);
        return drivers;
    }

    @GetMapping("/clients")
    public List<UserApp> getClients() {
        Role driverRole = roleRepository.findByName("CLIENT");

// Finding users by the retrieved role object
        List<UserApp> client = userRepository.findByRole(driverRole);
        return client;
    }
}
