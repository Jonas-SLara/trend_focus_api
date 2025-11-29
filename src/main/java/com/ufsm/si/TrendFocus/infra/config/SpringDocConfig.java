package com.ufsm.si.TrendFocus.infra.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title="TrendFocus API - Hub de Notícias Acadêmicas",
        description = """
            API REST desenvolvida em Spring Boot para o gerenciamento de Notícias, 
            Tópicos e Análises de Tendências Acadêmicas.  
            Inclui autenticação JWT, controle de permissões e endpoints públicos e restritos.
            """,
        contact = @Contact(
            name = "Jonas Silva de Lara",
            email = "jonassilvadelara@gmail.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:3000/api", description = "Servidor local de desenvolvimento")
    }
    /*
    security = {
        @SecurityRequirement(name = "bearerAuth") // aplica globalmente o token JWT
    }
    */
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER,
    description = """
        Autenticação baseada em JWT.  
        Insira o token obtido no endpoint `/login` no formato:  
        **Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...**
        """
)
public class SpringDocConfig {

}
