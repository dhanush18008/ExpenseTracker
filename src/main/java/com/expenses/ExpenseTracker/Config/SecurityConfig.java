package com.expenses.ExpenseTracker.Config;

import com.expenses.ExpenseTracker.CustomPropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CustomPropertyConfig c;
    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        String user_username = c.getProperty("user.username");
        String user_password = c.getProperty("user.password");
        String user_role = c.getProperty("user.role");

        UserDetails user1= User.builder()
                .username(user_username)
                .password(passwordEncoder().encode(user_password))
                .roles(user_role)
                .build();
        String admin_username = c.getProperty("admin.username");
        String admin_password = c.getProperty("admin.password");
        String admin_role = c.getProperty("admin.role");
        UserDetails admin = User.builder()
                .username(admin_username)
                .password(passwordEncoder().encode(admin_password))
                .roles(admin_role)
                .build();
        return new InMemoryUserDetailsManager(user1,admin);

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