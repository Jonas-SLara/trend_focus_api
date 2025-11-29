package com.ufsm.si.TrendFocus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufsm.si.TrendFocus.dto.request.UsuarioRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.UsuarioDTO;
import com.ufsm.si.TrendFocus.model.Usuario;
import com.ufsm.si.TrendFocus.model.enums.Role;
import com.ufsm.si.TrendFocus.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO salvar(UsuarioRegisterDTO ur){
        Usuario usuario = new Usuario();
        usuario.setSenha(passwordEncoder.encode(ur.getSenha()));
        usuario.setNome(ur.getNome());
        usuario.setEmail(ur.getEmail());
        usuario.setAtivo(false);
        usuario.setDataCriado(LocalDateTime.now());
        usuario.setTipo(Role.ROLE_ANALISTA); //por padrão é analista de dados
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> listar(){
        return this.usuarioRepository.findAll().stream()
        .map(usuario -> new UsuarioDTO(usuario))
        .toList();
    }
}
