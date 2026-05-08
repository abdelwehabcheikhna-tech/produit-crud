package com.produit.produit.service;

import com.produit.produit.entity.Produit;
import com.produit.produit.repository.ProduitRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;



    public Produit creerProduit(@Valid Produit produit) {
        // Validation supplémentaire
        if (produit.getNom() == null || produit.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du produit est obligatoire");
        }

        if ((produit.getPrix()) == 0|| (produit.getPrix() <= 0)) {
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        }
        if (produit.getStock() == null || produit.getStock() < 0) {
            produit.setStock(0);
        }

        // Définir la disponibilité en fonction du stock
        produit.setDisponible(produit.getStock() > 0);

        return produitRepository.save(produit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> lire() {
        List<Produit> produits = produitRepository.findAll();
        if (produits.isEmpty()) {
            throw new RuntimeException("Aucun produit trouvé dans la base de données");
        }
        return produits;
    }

    @Override
    public Page<Produit> lireAvecPagination(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public Produit modifierProduit(Long id, Produit produit) {
        return produitRepository.findById(id)
                .map(p -> {
                    // Mise à jour des champs
                    if (produit.getNom() != null && !produit.getNom().trim().isEmpty()) {
                        p.setNom(produit.getNom());
                    }
                    if (produit.getDescription() != null) {
                        p.setDescription(produit.getDescription());
                    }
                    if (produit.getStock() != null) {
                        p.setStock(produit.getStock());
                        // Mettre à jour la disponibilité en fonction du stock
                        p.setDisponible(produit.getStock() > 0);
                    }
                    if (produit.getImageUrl() != null) {
                        p.setImageUrl(produit.getImageUrl());
                    }

                    return produitRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }

    @Override
    @Transactional
    public String supprimerProduit(Long id) {
        // Vérifier si le produit existe avant suppression
        if (!produitRepository.existsById(id)) {
            throw new RuntimeException("Produit non trouvé avec l'id: " + id);
        }
        produitRepository.deleteById(id);
        return "Produit supprimé avec succès";
    }

    @Override
    @Transactional(readOnly = true)
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }

    @Override
    public Optional<Produit> findProduitById(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> getProduitsDisponibles() {
        List<Produit> produits = produitRepository.findByDisponibleTrue();
        if (produits.isEmpty()) {
            throw new RuntimeException("Aucun produit disponible pour le moment");
        }
        return produits;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> searchProduits(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return lire();
        }
        return produitRepository.findByNomContainingIgnoreCase(keyword);
    }

    @Override
    public List<Produit> getProduitsByPrixRange(Double min, Double max) {
        return List.of();
    }

    @Override
    public Produit getProduitByNom(String nom) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierStock(Long id, int quantiteDemandee) {
        Produit produit = getProduitById(id);
        if (produit.getStock() == null) {
            return false;
        }
        return produit.getStock() >= quantiteDemandee;
    }

    @Override
    @Transactional
    public void reduireStock(Long id, int quantite) {
        Produit produit = getProduitById(id);
        if (produit.getStock() >= quantite) {
            produit.setStock(produit.getStock() - quantite);
            produit.setDisponible(produit.getStock() > 0);
            produitRepository.save(produit);
        } else {
            throw new RuntimeException("Stock insuffisant pour le produit: " + produit.getNom());
        }
    }

    @Override
    @Transactional
    public void augmenterStock(Long id, int quantite) {
        Produit produit = getProduitById(id);
        produit.setStock(produit.getStock() + quantite);
        produit.setDisponible(true);
        produitRepository.save(produit);
    }

    @Override
    public void mettreAJourDisponibilite(Long id) {

    }

    @Override
    @Transactional(readOnly = true)
    public Long countProduits() {
        return produitRepository.count();
    }

    @Override
    public Long countProduitsDisponibles() {
        return 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> getProduitsEnRupture() {
        return produitRepository.findByStockLessThanEqual(5);
    }

    @Override
    public Double calculerValeurStockTotale() {
        return 0.0;
    }

    @Override
    public void supprimerTousLesProduits() {

    }

    @Override
    public void mettreAJourPrixBatch(Double pourcentage) {

    }

    @Override
    public @Nullable Object getAllProduits() {
        return null;
    }
}