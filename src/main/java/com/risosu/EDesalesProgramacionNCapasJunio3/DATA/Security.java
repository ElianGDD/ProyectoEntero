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

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/Presentacion/CargaMasiva").hasAuthority("ADMIN")
                .requestMatchers("/Presentacion/Login").permitAll()
                .requestMatchers("/Presentacion").hasAnyAuthority("ADMIN", "ANALISTA", "PROGRAMADOR")
                .requestMatchers("/Presentacion/**").hasAuthority("PROGRAMADOR")
                .anyRequest().authenticated()
        )
                .formLogin(form -> form
                .loginPage("/Presentacion/Login")
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
        UserDetails admin = User.builder()
                .username("Kevin")
                .password(passwordEncoder().encode("password2"))
                .authorities("ADMIN")
                .build();
        UserDetails analista = User.builder()
                .username("mago1")
                .password(passwordEncoder().encode("password3"))
                .authorities("ANALISTA")
                .build();
        return new InMemoryUserDetailsManager(programador, admin, analista);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
