package com.example.projetetudiant.security;

import com.example.projetetudiant.security.services.userDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class config extends WebSecurityConfigurerAdapter {
    private userDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/SignUp", "/saveUser").permitAll() // Allow access to SignUp without authentication
                .antMatchers("/login", "/template", "/accessDenied", "/webjars/**", "/css/**", "/js/**", "/image/**").permitAll()
                .antMatchers("/createProduit", "/editeProduit","/listeUser").hasAuthority("ADMIN")
                .antMatchers("/createProduit").hasAnyAuthority("ADMIN", "AGENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/listeProduits")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable() // Disable CSRF protection for simplicity
                .headers().frameOptions().disable(); // Disable frame options for using iframe


    }


}
