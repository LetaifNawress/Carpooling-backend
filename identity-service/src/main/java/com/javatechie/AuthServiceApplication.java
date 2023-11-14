package com.javatechie;

import com.javatechie.entity.Role;
import com.javatechie.entity.UserApp;
import com.javatechie.repository.RoleRepository;
import com.javatechie.repository.UserRepository;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public void initializeDefaultData() {
		Role adminRole = new Role();
		adminRole.setName("ADMIN");
		roleRepository.save(adminRole);
		Role DRIVER = new Role();
		DRIVER.setName("DRIVER");
		roleRepository.save(DRIVER);

		UserApp adminUser = new UserApp();
		adminUser.setName("admin");
		adminUser.setEmail("admin@admin.com");
		adminUser.setPassword(passwordEncoder.encode("123"));
		adminUser.setRole(adminRole);
		userRepository.save(adminUser);
		UserApp DRIVER1 = new UserApp();
		adminUser.setName("driver");
		adminUser.setEmail("driver@driver.com");
		adminUser.setPassword(passwordEncoder.encode("123"));
		adminUser.setRole(DRIVER);
		userRepository.save(DRIVER1);
	}
}


