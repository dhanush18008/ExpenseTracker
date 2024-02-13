package com.expenses.ExpenseTracker.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        UserDetails user1= User.builder()
                .username("dhanush")
                .password(passwordEncoder().encode("qak076269#"))
                .roles("User")
                .build();
        return new InMemoryUserDetailsManager(user1);

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth)->auth
                        .anyRequest()
                        .authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.headers(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}