package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/panier")
@SessionAttributes("panier")
public class PanierController {

    private final ProduitRepository produitRepository;

    public PanierController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @ModelAttribute("panier")
    public List<Produit> panier() {
        return new ArrayList<>();
    }

    @GetMapping
    public String afficherPanier(@ModelAttribute("panier") List<Produit> panier, Model model) {
        double total = panier.stream().mapToDouble(Produit::getPrix).sum();
        model.addAttribute("panier", panier);
        model.addAttribute("total", total);
        return "panier";
    }

    @GetMapping("/add/{id}")
    public String ajouterAuPanier(@PathVariable Long id,
                                  @ModelAttribute("panier") List<Produit> panier,
                                  Model model) {
        produitRepository.findById(id).ifPresent(produit -> {
            panier.add(produit);
        });
        return "redirect:/panier";
    }

    @GetMapping("/remove/{id}")
    public String retirerDuPanier(@PathVariable Long id,
                                  @ModelAttribute("panier") List<Produit> panier) {
        panier.removeIf(p -> p.getId().equals(id));
        return "redirect:/panier";
    }

    @GetMapping("/clear")
    public String viderPanier(@ModelAttribute("panier") List<Produit> panier,
                              SessionStatus sessionStatus) {
        panier.clear();
        sessionStatus.setComplete();
        return "redirect:/panier";
    }
}