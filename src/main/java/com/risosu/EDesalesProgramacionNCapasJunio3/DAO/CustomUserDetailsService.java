package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.UsuarioJPARepositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Alien 13
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioJPARepositoryDAO usuarioJPARepositoryDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioJPARepositoryDAO.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userName));
        return User.builder()
                .username(usuario.getUserName())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .authorities(new SimpleGrantedAuthority(usuario.getRoll().getNombre())) 
                .build();
    }
}
