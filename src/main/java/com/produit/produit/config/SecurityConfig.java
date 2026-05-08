package com.produit.produit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuration CSRF
                .csrf(csrf -> csrf
                        // Désactiver CSRF pour l'API REST (Postman)
                        .ignoringRequestMatchers("/api/**")
                        // Activer CSRF pour les formulaires web
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )

                // Configuration des autorisations
                .authorizeHttpRequests(auth -> auth
                        // Pages publiques
                        .requestMatchers("/", "/home", "/register", "/login", "/logout",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        // API REST (accessible sans authentification pour les tests)
                        .requestMatchers("/api/**").permitAll()

                        // Administration des produits (réservé aux admins)
                        .requestMatchers("/produits/**", "/admin/**").hasRole("ADMIN")

                        // Catalogue, panier, commandes (USER ou ADMIN)
                        .requestMatchers("/catalogue/**", "/panier/**", "/commande/**",
                                "/mes-commandes", "/profile").hasAnyRole("USER", "ADMIN")

                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )

                // Configuration du formulaire de connexion
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/catalogue", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )

                // Configuration de la déconnexion
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )

                // Configuration "Se souvenir de moi"
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKeyForRememberMe")
                        .tokenValiditySeconds(86400) // 24 heures
                        .rememberMeParameter("remember-me")
                        .userDetailsService(customUserDetailsService)
                )

                // Configuration du service UserDetails
                .userDetailsService(customUserDetailsService)

                // Gestion des sessions
                .sessionManagement(session -> session
                        .maximumSessions(1) // Une seule session par utilisateur
                        .expiredUrl("/login?expired=true")
                );

        return http.build();
    }
}