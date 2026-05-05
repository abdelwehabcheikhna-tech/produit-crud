package com.produit.produit.controller;

import com.produit.produit.entity.Commande;
import com.produit.produit.entity.Produit;
import com.produit.produit.repository.CommandeRepository;
import com.produit.produit.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commande")
@SessionAttributes("panier")
public class CommandeController {

    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;

    public CommandeController(CommandeRepository commandeRepository,
                              ProduitRepository produitRepository) {
        this.commandeRepository = commandeRepository;
        this.produitRepository = produitRepository;
    }

    @GetMapping("/acheteur/{nom}")
    public String afficherCommandesParAcheteur(@PathVariable String nom, Model model) {
        List<Commande> commandes = commandeRepository.findByAcheteur(nom);
        model.addAttribute("commandes", commandes);
        model.addAttribute("acheteur", nom);
        return "commandes-par-acheteur";
    }

    @PostMapping("/valider")
    public String validerCommande(@ModelAttribute("panier") List<Produit> panier,
                                  Model model,
                                  SessionStatus sessionStatus) {

        System.out.println("=== DÉBUT VALIDATION COMMANDE ===");
        System.out.println("Panier reçu: " + (panier != null ? panier.size() : "null"));

        // Vérification du panier
        if (panier == null || panier.isEmpty()) {
            model.addAttribute("error", "Votre panier est vide !");
            return "panier";
        }

        try {
            // Création de la commande
            Commande commande = new Commande();
            commande.setAcheteur(getCurrentUsername());

            // Copier les produits pour éviter les problèmes de session
            List<Produit> produitsCommande = new ArrayList<>(panier);
            commande.setProduits(produitsCommande);

            // Calcul du total
            double total = panier.stream()
                    .mapToDouble(Produit::getPrix)
                    .sum();
            commande.setTotal(total);

            System.out.println("Commande à sauvegarder - Acheteur: " + commande.getAcheteur());
            System.out.println("Nombre de produits: " + commande.getProduits().size());
            System.out.println("Total: " + commande.getTotal());

            // Sauvegarde
            Commande savedCommande = commandeRepository.save(commande);
            System.out.println("Commande sauvegardée avec ID: " + savedCommande.getId());

            // Vider le panier
            panier.clear();
            sessionStatus.setComplete();

            model.addAttribute("commande", savedCommande);
            model.addAttribute("success", "Votre commande a été validée avec succès !");

            System.out.println("=== FIN VALIDATION COMMANDE SUCCÈS ===");
            return "commande-confirmation";

        } catch (Exception e) {
            System.err.println("ERREUR lors de la validation: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de la validation: " + e.getMessage());
            return "panier";
        }
    }

    // Méthode pour récupérer l'utilisateur connecté
    private String getCurrentUsername() {
        // Récupérer depuis Spring Security
        org.springframework.security.core.Authentication auth =
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() &&
                !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"))) {
            return auth.getName();
        }
        return "client@test.com";
    }
}