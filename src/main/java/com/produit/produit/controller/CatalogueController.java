package com.produit.produit.controller;

import com.produit.produit.service.PanierService;
import com.produit.produit.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private PanierService panierService;

    @GetMapping
    public String afficherCatalogue(Model model, Authentication authentication) {
        try {
            model.addAttribute("produits", produitService.getProduitsDisponibles());
        } catch (Exception e) {
            model.addAttribute("produits", new ArrayList<>());
            model.addAttribute("error", "Aucun produit disponible");
        }

        // Utiliser le username au lieu de cast
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("panier", panierService.getOrCreatePanierByUsername(username));
        }

        return "catalogue";
    }

    @GetMapping("/rechercher")
    public String rechercherProduits(@RequestParam("keyword") String keyword, Model model) {
        try {
            model.addAttribute("produits", produitService.searchProduits(keyword));
        } catch (Exception e) {
            model.addAttribute("produits", new ArrayList<>());
        }
        model.addAttribute("keyword", keyword);
        return "catalogue";
    }
}