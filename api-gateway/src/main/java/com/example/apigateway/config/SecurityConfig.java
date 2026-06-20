package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/eureka/**").permitAll()

                        // * RUTAS DE PRODUCTOS
                        .pathMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .pathMatchers("/products/**").hasRole("ADMIN")

                        // * RUTAS DE INVENTARIOS
                        .pathMatchers(HttpMethod.GET, "/inventory/**").permitAll()
                        .pathMatchers("/inventory/**").hasRole("ADMIN")

                        // * RUTAS DE ORDENES
                        .pathMatchers(HttpMethod.POST, "/orders").hasRole("USER")
                        .pathMatchers("/orders/**").hasRole("ADMIN")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        // * UTILIZAMOS EL CONVERTIDOR DE ROLES DE JWT
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(this.reactiveJwtAuthenticationConverterAdapter())));
        return http.build();
    }

    // * METODO TRADUCTOR DE ROLES ENTRE SPRING SECURITY Y KEYCLOAK
    private ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
        // * GENERACION DE CONVERTIDOR
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // * DEL JWT QUE DA KEYCLOAK, SACAMOS LOS real_access
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

            // * VALIDACION DE QUE LOS REAL_ACCESS EXISTAN
            if (realmAccess == null || realmAccess.isEmpty()) {
                return Collections.emptyList();
            }

            // * SACAMOS LOS ROLES
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            return roles.stream()
                    .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol))
                    .collect(Collectors.toList());
        });

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}
