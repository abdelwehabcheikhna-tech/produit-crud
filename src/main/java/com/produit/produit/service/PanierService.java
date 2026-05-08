package com.produit.produit.service;

import com.produit.produit.entity.Panier;
import com.produit.produit.entity.User;
import com.produit.produit.repository.PanierRepository;
import com.produit.produit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private UserRepository userRepository;

    // Méthode qui prend un objet User
    public Panier getOrCreatePanier(User user) {
        return panierRepository.findByUser(user)
                .orElseGet(() -> {
                    Panier nouveauPanier = new Panier();
                    nouveauPanier.setUser(user);
                    nouveauPanier.setTotal(0.0);
                    return panierRepository.save(nouveauPanier);
                });
    }

    // Méthode qui prend un String (username)
    public Panier getOrCreatePanierByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));
        return getOrCreatePanier(user);
    }
}