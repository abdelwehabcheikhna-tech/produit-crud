package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.service.ProduitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
@AllArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping("/create")
    public Produit createProduit(@RequestBody Produit produit) {
        return produitService.creerProduit(produit);
    }

    @GetMapping("/read")
    public List<Produit> read() {
        return produitService.lire();
    }

    @GetMapping("/read/{id}")
    public Produit readById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    @PutMapping("/update/{id}")
    public Produit update(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.modifierProduit(id, produit);
    }

    @DeleteMapping("/delete/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        return produitService.supprimerProduit(id);
    }
}
