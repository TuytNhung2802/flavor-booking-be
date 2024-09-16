package com.flavorbooking.configurations;

import com.flavorbooking.exceptions.JwtAuthenEntrypoint;
import com.flavorbooking.filters.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenEntrypoint jwtAuthenticationEntryPoint;
    private final LogoutHandler logoutHandler;  // AuthService implementation

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // filter per request JwtTokenFilter class.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(HttpMethod.GET, "api/**").permitAll()
                            .requestMatchers("/auth/api/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/filter-product").permitAll()
                            .requestMatchers(HttpMethod.POST,"api/register-restaurant/**").permitAll()
                            .anyRequest().authenticated();
                })
                // indicating no session will be created for authenticated users
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // return  401 instead 403
                .logout(logout ->
                        logout.logoutUrl("/auth/api/logout") // default url: "/logout"
                                .addLogoutHandler(logoutHandler) // LogoutService class.
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }


}
