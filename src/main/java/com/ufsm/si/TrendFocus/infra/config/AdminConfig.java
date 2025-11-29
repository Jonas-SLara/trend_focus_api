package com.ufsm.si.TrendFocus.infra.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufsm.si.TrendFocus.model.Usuario;
import com.ufsm.si.TrendFocus.model.enums.Role;
import com.ufsm.si.TrendFocus.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {

    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String ADMIN_EMAIL;

    @Value("${admin.password}")
    private String ADMIN_PASSWORD;

    @Value("${admin.name}")
    private String ADMIN_NAME;

    @Bean
    @Transactional
    public CommandLineRunner initAdmin(UsuarioRepository uRepository){
        return args -> {
            if(uRepository.findByEmail(ADMIN_EMAIL).isEmpty()){
                String crip = passwordEncoder.encode(ADMIN_PASSWORD);
                Usuario admin = new Usuario();
                admin.setEmail(ADMIN_EMAIL);
                admin.setNome(ADMIN_NAME);
                admin.setSenha(crip);
                admin.setTipo(Role.ROLE_ADM);
                admin.setDataCriado(LocalDateTime.now());
                uRepository.save(admin);
                System.out.println(ADMIN_EMAIL + " Usuario Cadastrado com sucesso! ");
            }
            else{
                System.out.println("Usuário adm já existente: " + ADMIN_EMAIL);
            }
        };
    }

}
