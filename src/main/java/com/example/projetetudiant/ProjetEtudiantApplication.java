package com.example.projetetudiant;


import com.example.projetetudiant.security.services.iservice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjetEtudiantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetEtudiantApplication.class, args);
    }
    @Bean
    CommandLineRunner start1(iservice serviceImpl){
        return args -> {

            serviceImpl.addRole("ADMIN","");
            serviceImpl.addRole("AGENT","");
            serviceImpl.addRole("USER","");
            serviceImpl.addUser("naim","1234","1234");
            serviceImpl.addRoleToUser("naim","ADMIN");

        } ;
    }




    @Bean
   PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
   }

}
