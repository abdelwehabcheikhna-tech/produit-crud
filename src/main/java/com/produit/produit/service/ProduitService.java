package com.produit.produit.service;

import com.produit.produit.entity.Produit;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    // ===== CRUD de base =====

    /**
     * Créer un nouveau produit
     *
     * @param produit Le produit à créer
     * @return Le produit créé avec son ID
     */
    Produit creerProduit(@Valid Produit produit);

    /**
     * Récupérer tous les produits
     * @return Liste de tous les produits
     */
    List<Produit> lire();

    /**
     * Récupérer tous les produits avec pagination
     * @param pageable Informations de pagination
     * @return Page de produits
     */
    Page<Produit> lireAvecPagination(Pageable pageable);

    /**
     * Modifier un produit existant
     * @param id ID du produit à modifier
     * @param produit Nouvelles informations du produit
     * @return Produit modifié
     */
    Produit modifierProduit(Long id, @Valid Produit produit);

    /**
     * Supprimer un produit
     * @param id ID du produit à supprimer
     * @return Message de confirmation
     */
    String supprimerProduit(Long id);

    /**
     * Récupérer un produit par son ID
     * @param id ID du produit
     * @return Produit trouvé
     * @throws RuntimeException si le produit n'existe pas
     */
    Produit getProduitById(Long id);

    /**
     * Récupérer un produit par son ID (Optionnel)
     * @param id ID du produit
     * @return Optional contenant le produit ou vide
     */
    Optional<Produit> findProduitById(Long id);

    // ===== Méthodes de recherche =====

    /**
     * Récupérer les produits disponibles (en stock)
     * @return Liste des produits disponibles
     */
    List<Produit> getProduitsDisponibles();

    /**
     * Rechercher des produits par mot-clé (nom ou description)
     * @param keyword Mot-clé à rechercher
     * @return Liste des produits correspondants
     */
    List<Produit> searchProduits(String keyword);

    /**
     * Rechercher des produits par fourchette de prix
     * @param min Prix minimum
     * @param max Prix maximum
     * @return Liste des produits dans la fourchette
     */
    List<Produit> getProduitsByPrixRange(Double min, Double max);

    /**
     * Rechercher des produits par nom exact
     * @param nom Nom du produit
     * @return Produit trouvé ou null
     */
    Produit getProduitByNom(String nom);

    // ===== Gestion du stock =====

    /**
     * Vérifier si le stock est suffisant
     * @param id ID du produit
     * @param quantiteDemandee Quantité demandée
     * @return true si stock suffisant, false sinon
     */
    boolean verifierStock(Long id, int quantiteDemandee);

    /**
     * Réduire le stock d'un produit
     * @param id ID du produit
     * @param quantite Quantité à retirer
     * @throws RuntimeException si stock insuffisant
     */
    void reduireStock(Long id, int quantite);

    /**
     * Augmenter le stock d'un produit
     * @param id ID du produit
     * @param quantite Quantité à ajouter
     */
    void augmenterStock(Long id, int quantite);

    /**
     * Mettre à jour la disponibilité d'un produit en fonction du stock
     * @param id ID du produit
     */
    void mettreAJourDisponibilite(Long id);

    // ===== Statistiques =====

    /**
     * Compter le nombre total de produits
     * @return Nombre de produits
     */
    Long countProduits();

    /**
     * Compter le nombre de produits disponibles
     * @return Nombre de produits disponibles
     */
    Long countProduitsDisponibles();

    /**
     * Récupérer les produits en rupture de stock (stock <= 5)
     * @return Liste des produits en rupture
     */
    List<Produit> getProduitsEnRupture();

    /**
     * Calculer la valeur totale du stock
     * @return Valeur totale du stock
     */
    Double calculerValeurStockTotale();

    // ===== Méthodes de masse =====

    /**
     * Supprimer tous les produits (attention!)
     */
    void supprimerTousLesProduits();

    /**
     * Mettre à jour les prix avec un pourcentage
     * @param pourcentage Pourcentage d'augmentation (positif) ou réduction (négatif)
     */
    void mettreAJourPrixBatch(Double pourcentage);

    @Nullable Object getAllProduits();
}