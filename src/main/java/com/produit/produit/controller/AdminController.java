package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/produits")
    public String listProduits(Model model) {
        List<Produit> produits = produitService.lire();
        model.addAttribute("produits", produits);
        return "admin/produits";
    }

    @GetMapping("/produits/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("titre", "Ajouter un produit");
        return "admin/form-produit";
    }

    @PostMapping("/produits/ajouter")
    public String addProduit(@Valid @ModelAttribute("produit") Produit produit,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/form-produit";
        }
        produitService.creerProduit(produit);
        redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté avec succès!");
        return "redirect:/admin/produits";
    }

    @GetMapping("/produits/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produit", produit);
        model.addAttribute("titre", "Modifier le produit");
        return "admin/form-produit";
    }

    @PostMapping("/produits/modifier/{id}")
    public String updateProduit(@PathVariable Long id,
                                @Valid @ModelAttribute("produit") Produit produit,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/form-produit";
        }
        produitService.modifierProduit(id, produit);
        redirectAttributes.addFlashAttribute("successMessage", "Produit modifié avec succès!");
        return "redirect:/admin/produits";
    }

    @GetMapping("/produits/supprimer/{id}")
    public String deleteProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produitService.supprimerProduit(id);
            redirectAttributes.addFlashAttribute("successMessage", "Produit supprimé avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression");
        }
        return "redirect:/admin/produits";
    }
}