package com.ufsm.si.TrendFocus.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter filtro){
        this.securityFilter = filtro;
    }

    /*
     * Define como o spring security protege as requisições http
     * construindo uma pipeline de filtros e regras para cada requisição
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                .requestMatchers(HttpMethod.GET, "/usuario").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.GET, "/topico").permitAll()
                .requestMatchers(HttpMethod.POST, "/topico").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.DELETE, "/topico").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.GET, "/termo").permitAll()
                .requestMatchers(HttpMethod.POST, "/termo").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.DELETE, "/termo").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.GET, "/noticia", "/noticia/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/noticia").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(HttpMethod.POST, "/noticia").hasAnyRole("ADM", "ANALISTA")
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/webjars/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /* Este método retorna uma instância de AuthenticationManager.
     * Essa instância é utilizada pelo Spring Security para realizar a
     * autenticação de um usuário. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception{
        return config.getAuthenticationManager();
    }

    /* passwordEncoder(): Este método retorna uma instância de PasswordEnconder
     * que é utilizada pelo Spring Security para codificar as senhas dos usuários
     * de forma segura, */
    @Bean
    public PasswordEncoder passwordEncoder() throws Exception{
        return new BCryptPasswordEncoder();
    }
}