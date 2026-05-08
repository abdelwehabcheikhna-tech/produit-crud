package com.produit.produit.service;

import com.produit.produit.entity.User;
import com.produit.produit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche de l'utilisateur par username ou email
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom ou email: " + username)));

        // Vérifier si le compte est activé
        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("Compte désactivé. Veuillez contacter l'administrateur.");
        }

        // Mettre à jour la date de dernière connexion
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Créer les autorités (rôles)
        List<SimpleGrantedAuthority> authorities = getAuthorities(user);

        // Retourner l'objet UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),     // enabled
                true,                 // accountNonExpired
                true,                 // credentialsNonExpired
                true,                 // accountNonLocked
                authorities
        );
    }

    /**
     * Récupère les autorités (rôles) de l'utilisateur
     */
    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        // Si l'utilisateur a plusieurs rôles (ex: ROLE_ADMIN, ROLE_USER)
        String role = user.getRole();

        if (role == null || role.isEmpty()) {
            role = "USER";
        }

        // Ajouter le préfixe ROLE_ si nécessaire
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    /**
     * Charge un utilisateur par son ID (utile pour certaines opérations)
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'id: " + id));

        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("Compte désactivé");
        }

        List<SimpleGrantedAuthority> authorities = getAuthorities(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }

    /**
     * Vérifie si l'utilisateur a un rôle spécifique
     */
    @Transactional(readOnly = true)
    public boolean userHasRole(String username, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        return user.getRole() != null && user.getRole().equalsIgnoreCase(role);
    }

    /**
     * Active ou désactive un utilisateur
     */
    @Transactional
    public void setUserEnabled(String username, boolean enabled) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        user.setEnabled(enabled);
        userRepository.save(user);
    }

    /**
     * Change le rôle d'un utilisateur
     */
    @Transactional
    public void changeUserRole(String username, String newRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        user.setRole(newRole);
        userRepository.save(user);
    }
}