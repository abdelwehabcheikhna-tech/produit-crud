package com.produit.produit.controller;

import com.produit.produit.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {

    private final ProduitRepository produitRepository;

    public CatalogueController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping
    public String afficherCatalogue(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "catalogue";
    }
}