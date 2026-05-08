package com.produit.produit.controller;

import com.produit.produit.entity.Produit;
import com.produit.produit.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ApiProduitController {

    @Autowired
    private ProduitService produitService;

    // Créer un produit (POST)
    @PostMapping
    public ResponseEntity<?> creerProduit(@RequestBody Produit produit) {
        try {
            Produit nouveauProduit = produitService.creerProduit(produit);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauProduit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("❌ Erreur: " + e.getMessage());
        }
    }

    // Lister tous les produits (GET)
    @GetMapping
    public ResponseEntity<List<Produit>> listerProduits() {
        List<Produit> produits = produitService.lire();
        return ResponseEntity.ok(produits);
    }

    // Récupérer un produit par ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduitById(@PathVariable Long id) {
        try {
            Produit produit = produitService.getProduitById(id);
            return ResponseEntity.ok(produit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Produit non trouvé avec l'id: " + id);
        }
    }

    // Modifier un produit (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierProduit(@PathVariable Long id, @RequestBody Produit produit) {
        try {
            Produit produitModifie = produitService.modifierProduit(id, produit);
            return ResponseEntity.ok(produitModifie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ " + e.getMessage());
        }
    }

    // Supprimer un produit (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerProduit(@PathVariable Long id) {
        try {
            String message = produitService.supprimerProduit(id);
            return ResponseEntity.ok("✅ " + message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ " + e.getMessage());
        }
    }
}