package com.risosu.EDesalesProgramacionNCapasJunio3.DATA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Security {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Presentacion/Login").permitAll()
                .requestMatchers("/Presentacion/CargaMasiva").hasAuthority("Admin")
                .requestMatchers("/Presentacion").hasAnyAuthority("Admin", "Analista", "Programador")
                .requestMatchers("/Presentacion/**").hasAuthority("Programador")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/Presentacion/Login")
                .defaultSuccessUrl("/Presentacion", true)
                .failureUrl("/Presentacion/Login?error=true")
                .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails programador = User.builder()
//                .username("Elian")
//                .password(passwordEncoder().encode("password1"))
//                .authorities("PROGRAMADOR")
//                .build();
//        UserDetails admin = User.builder()
//                .username("Kevin")
//                .password(passwordEncoder().encode("password2"))
//                .authorities("ADMIN")
//                .build();
//        UserDetails analista = User.builder()
//                .username("mago1")
//                .password(passwordEncoder().encode("password3"))
//                .authorities("ANALISTA")
//                .build();
//        return new InMemoryUserDetailsManager(programador, admin, analista);
//    }
}
