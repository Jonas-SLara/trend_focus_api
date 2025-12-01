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

    private static final String[] SWAGGER_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/api-docs/**",
        "/webjars/**"
    };
    
    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter filtro){
        this.securityFilter = filtro;
    }
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .cors(cors -> {})
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
                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws Exception{
        return new BCryptPasswordEncoder();
    }
}
