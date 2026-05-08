package com.produit.produit.config;

import com.produit.produit.entity.User;
import com.produit.produit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Créer admin s'il n'existe pas
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);
            admin.setFullName("Administrateur");
            admin.setCreatedAt(java.time.LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("✅ Compte ADMIN créé: admin / password");
        }

        // Créer client s'il n'existe pas
        if (userRepository.findByUsername("client").isEmpty()) {
            User client = new User();
            client.setUsername("client");
            client.setEmail("client@example.com");
            client.setPassword(passwordEncoder.encode("1234"));
            client.setRole("USER");
            client.setEnabled(true);
            client.setFullName("Client Test");
            client.setCreatedAt(java.time.LocalDateTime.now());
            userRepository.save(client);
            System.out.println("✅ Compte CLIENT créé: client / 1234");
        }
    }
}