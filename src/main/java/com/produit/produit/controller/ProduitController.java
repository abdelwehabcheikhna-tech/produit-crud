package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // Afficher tous les produits (pour admin)
    @GetMapping
    public String listProduits(Model model) {
        model.addAttribute("produits", produitService.getAllProduits());
        return "produits/liste";
    }

    // Formulaire d'ajout
    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        return "produits/ajouter";
    }

    // Ajouter un produit
    @PostMapping("/ajouter")
    public String addProduit(@Valid @ModelAttribute("produit") Produit produit,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "produits/ajouter";
        }

        produitService.creerProduit(produit);
        redirectAttributes.addFlashAttribute("success", "Produit ajouté avec succès!");
        return "redirect:/produits";
    }

    // Formulaire de modification
    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produit", produit);
        return "produits/modifier";
    }

    // Modifier un produit
    @PostMapping("/modifier/{id}")
    public String updateProduit(@PathVariable Long id,
                                @Valid @ModelAttribute("produit") Produit produit,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "produits/modifier";
        }

        produitService.creerProduit(produit);
        redirectAttributes.addFlashAttribute("success", "Produit modifié avec succès!");
        return "redirect:/produits";
    }

    // Supprimer un produit
    @GetMapping("/supprimer/{id}")
    public String deleteProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        @Valid Produit produit = null;
        produitService.creerProduit(produit);
        redirectAttributes.addFlashAttribute("success", "Produit supprimé avec succès!");
        return "redirect:/produits";
    }
}
