package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.service.ProduitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProduitViewController {

    private final ProduitService produitService;

    public ProduitViewController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/produits")
    public String afficherProduits(Model model) {
        List<Produit> produits = produitService.lire();
        model.addAttribute("produits", produits);
        return "produits"; // correspond au fichier produits.html
    }

    @GetMapping("/produits/add")
    public String afficherFormulaire(Model model) {
        model.addAttribute("produit", new Produit());
        return "form-produit";
    }

    @PostMapping("/produits/save")
    public String enregistrerProduit(@ModelAttribute Produit produit, RedirectAttributes redirectAttributes) {
        if (produit.getId() != null) {
            produitService.modifierProduit(produit.getId(), produit);
            redirectAttributes.addFlashAttribute("successMessage", "Produit modifié avec succès !");
        } else {
            produitService.creerProduit(produit);
            redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté avec succès !");
        }
        return "redirect:/produits";
    }

    @GetMapping("/produits/delete/{id}")
    public String supprimerProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produitService.supprimerProduit(id);
            redirectAttributes.addFlashAttribute("successMessage", "Produit supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur : impossible de supprimer ce produit.");
        }
        return "redirect:/produits";
    }

    @GetMapping("/produits/edit/{id}")
    public String afficherFormulaireEdition(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produit", produit);
        return "form-produit"; // réutilise le même formulaire
    }
}
