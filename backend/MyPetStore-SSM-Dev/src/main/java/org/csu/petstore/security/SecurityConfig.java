package org.csu.petstore.security;

import org.csu.petstore.service.TokenBlackService;
import org.csu.petstore.service.impl.TokenBlackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private TokenBlackService tokenBlackService; // 新增依赖

    public SecurityConfig(TokenBlackService tokenBlackService) {
        this.tokenBlackService = tokenBlackService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/login","/api/v1/account","/api/v1/auth/login/forget"
                        ,"/carts","/carts/{itemId}","/favouriteList","/orders","/orders/{orderId}"
                        ,"/addresses/{addressId}","/orders/addresses", "/catalog/**", "/images/**", "/api/v1/auth/tokens/current")
                        .permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(
                    """ 
                    {"code":401,"message":"无效或过期的访问令牌"}
                    """
            );
        };
    }
}
