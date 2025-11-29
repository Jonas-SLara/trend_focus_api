package com.ufsm.si.TrendFocus.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenServiceJWT {

    @Value("${api.security.token.secret}")
    private String secret;
    private static final String issuer = "API TrendFocus";

    public String gerarToken(User usuario){
        try {
            //NUNCA EXPOR O SECRET NO CÓDIGO
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(TokenServiceJWT.issuer)
                .withSubject(usuario.getUsername())
                //.withClaim()
                .withExpiresAt(dataExpiracao())
                .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao criar o token JWT ", exception);
        }
    }

    //defina um instante de 2h30min no fuso horário de brasilia
    private Instant dataExpiracao(){
        LocalDateTime fim = LocalDateTime.now().plusHours(2).plusMinutes(30);
        return fim.toInstant(ZoneOffset.of("-03:00"));
    }

    //verifica se o token é válido, se sim extrai o usuário dele
    public String validaToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer(TokenServiceJWT.issuer)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}
