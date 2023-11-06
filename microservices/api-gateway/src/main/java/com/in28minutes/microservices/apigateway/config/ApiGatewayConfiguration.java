package com.in28minutes.microservices.apigateway.config;


import com.in28minutes.microservices.apigateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Optional use
//Problems appear during the process of adding security
//Somehow AuthenticationFilter is not applied for RouteLocator
//So i use workaround, which can be found in folder resources
//in file application.yml, there is added the routes for the gateway

//@Configuration
public class ApiGatewayConfiguration {

    @Autowired
    AuthenticationFilter authenticationFilterFactory;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/auth/**")
                        .filters(f -> f.filter(authenticationFilterFactory.apply(new AuthenticationFilter.Config())))
                        .uri("lb://identity-service"))
                .route(p -> p.path("/currency-tax-calculation/**")
                        .uri("lb://currency-tax-calculation"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}