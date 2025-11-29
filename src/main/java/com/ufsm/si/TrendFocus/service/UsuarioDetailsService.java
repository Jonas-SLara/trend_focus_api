package com.ufsm.si.TrendFocus.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
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
    public Usuario loadUserByUsername(String email){
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if(u.isEmpty()){
            throw new RuntimeException();
        }
        return u.get();
    }
}
