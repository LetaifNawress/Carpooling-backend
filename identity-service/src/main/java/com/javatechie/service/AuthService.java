package com.javatechie.service;

import com.javatechie.entity.Role;
import com.javatechie.entity.UserApp;
import com.javatechie.repository.RoleRepository;
import com.javatechie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserApp credential, String roleName) {

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        Role role = roleRepository.findByName(roleName);
        role.setName(roleName);
        credential.setRole(role);
        repository.save(credential);

        return "UserApp added to the system with the role: " + roleName;
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
