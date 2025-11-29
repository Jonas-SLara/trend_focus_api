package com.ufsm.si.TrendFocus.infra.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ufsm.si.TrendFocus.service.UsuarioDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Classe precisa extender uma outra classe para executa-la
 * uma vez por requisição através de um método chamado em cada requisição que:
 * extrai o token do cabeçalho
 * valida o token e se for válido busca o userDetails no banco
 * cria um objeto de autenticação e coloca o usuario autenticado no contexto do spring
 * e depois chama o próximo filtro
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenServiceJWT tokenServiceJWT;
    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityFilter(TokenServiceJWT tokenServiceJWT,
                          UsuarioDetailsService usuarioDetailsService){
        this.tokenServiceJWT = tokenServiceJWT;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.recoverToken(request);

        //caso tenha token, obter email através do token achar o usuario
        if(token != null){
            //caso o token não seja válido lança exceção
            String subject = tokenServiceJWT.validaToken(token);
            UserDetails userDetails = this.usuarioDetailsService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //passa para o próximo filtro
        filterChain.doFilter(request, response);
    }

    /*
     * Função auxiliar para recuperar o token do cabeçalho e formatar
     * retirando o tipo de token e deixando seu valor
    */
    private String recoverToken(HttpServletRequest request){
        String autHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(autHeader == null) return null;
        return autHeader.replace("Bearer", "").trim();
    }
}
