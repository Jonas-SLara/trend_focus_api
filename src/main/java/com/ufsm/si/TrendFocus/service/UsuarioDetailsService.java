package com.ufsm.si.TrendFocus.service;


import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ufsm.si.TrendFocus.model.Usuario;
import com.ufsm.si.TrendFocus.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository u){
        this.usuarioRepository = u;
    }

    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if(!u.isPresent()){
            System.out.println("usuario: " + email + " não encontrado");
            throw new UsernameNotFoundException("usuario: " + email + " não encontrado");
        }
        else{
            UserDetails usuarioDetails = User
            .withUsername(u.get().getEmail())
            .password(u.get().getSenha())
            .authorities(u.get().getAuthorities())
            .build();
            return usuarioDetails;
        }
    }
}
