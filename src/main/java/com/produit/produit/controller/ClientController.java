package com.produit.produit.controller;

import com.produit.produit.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private final ProduitRepository produitRepository;

    public ClientController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping("/client")
    public String interfaceClient(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "client";
    }
}