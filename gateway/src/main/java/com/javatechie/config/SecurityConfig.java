package com.javatechie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**", "/actuator/**").permitAll()
                .antMatchers("/driver/**").hasRole("DRIVER")
                .antMatchers("/participation/**").hasRole("CLIENT")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }
}

