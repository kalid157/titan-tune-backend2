package com.titan.tune.config;

import com.titan.tune.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // Preflight CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // Health check Render
                .requestMatchers("/actuator/health", "/actuator/health/**", "/health").permitAll()
                
                // Routes publiques d'authentification et Swagger
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Routes ajoutées pour tes tests (Catégories, Suivre, etc.)
                .requestMatchers("/categories/**").permitAll()
                .requestMatchers("/suivre/**").permitAll()
                .requestMatchers("/songEcouter/**").permitAll()
                
                // ⭐ LIGNES CORRIGÉES ICI : utilisation de .permitAll() avec parenthèses
                .requestMatchers(HttpMethod.POST, "/albums/create", "/albums/upload").permitAll()
                .requestMatchers(HttpMethod.POST, "/song/create", "/song/upload").permitAll()

                // Routes en lecture
                .requestMatchers(HttpMethod.GET, "/albums/**", "/song/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/favoris/stats/**").permitAll()
                
                // Routes sécurisées
                .requestMatchers(HttpMethod.DELETE, "/song/**").hasAnyRole("ARTIST", "ADMIN")
                
                // Tout le reste nécessite un JWT
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}