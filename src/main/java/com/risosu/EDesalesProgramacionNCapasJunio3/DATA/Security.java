package com.risosu.EDesalesProgramacionNCapasJunio3.DATA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Presentacion").hasAuthority("PROGRAMADOR")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .permitAll()
                .defaultSuccessUrl("/Presentacion", true)
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails programador = User.builder()
                .username("Elian")
                .password(passwordEncoder().encode("password1"))
                .authorities("PROGRAMADOR")
                .build();

        return new InMemoryUserDetailsManager(programador);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
