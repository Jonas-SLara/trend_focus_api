package com.ufsm.si.TrendFocus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufsm.si.TrendFocus.dto.request.UsuarioRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.UsuarioDTO;
import com.ufsm.si.TrendFocus.model.Usuario;
import com.ufsm.si.TrendFocus.model.enums.Role;
import com.ufsm.si.TrendFocus.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCrypt;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
        this.bCrypt = new BCryptPasswordEncoder(12);
    }

    public UsuarioDTO salvar(UsuarioRegisterDTO ur){
        Usuario usuario = new Usuario();
        usuario.setSenha(bCrypt.encode(ur.getSenha()));
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
