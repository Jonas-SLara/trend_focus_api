package com.ufsm.si.TrendFocus.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ufsm.si.TrendFocus.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3, max=50, message = "nome deve ter de 3 a 50 caracteres")
    @Column(name = "nome")
    private String nome;

    @Size(min=6, message="a senha tem que ter no mínimo 6 caracteres")
    @Column(name = "senha")
    private String senha;

    @Email(message = "email inválido")
    @NotBlank(message = "email é obrigatório")
    @Column(name = "email") 
    private String email;

    @Column(name = "created") 
    private LocalDateTime dataCriado;

    @Column(name = "ativo") 
    private boolean ativo;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Role tipo;

    /**
     * getAuthorities()
     * Retorna as permissões (roles) do usuário no formato que o Spring entende.
     * 
     * O Spring usa objetos do tipo GrantedAuthority para representar "papéis"
     * Exemplo: ROLE_ADM, ROLE_ANALISTA
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.tipo.name()));
    }

    /*Retorna a senha já criptografada*/
    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
