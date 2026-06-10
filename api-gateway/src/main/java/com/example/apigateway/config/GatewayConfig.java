package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocatorEurekaON(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route("inventory-service", route ->
                        route.path("/inventory/**") // ! RUTA BASE DEL CONTROLADOR DEL MS
                                .uri("lb://inventory-service") // ! NOMBRE DEL MS
                )
                .route("product-service", route ->
                        route.path("/products/**")
                                .uri("lb://product-service")
                )
                .route("order-service", route ->
                        route.path("/orders/**")
                                .uri("lb://order-service")
                )
                .build();
    }
}
