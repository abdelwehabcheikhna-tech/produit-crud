package com.produit.produit.repository;

import com.produit.produit.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    // Recherche par nom (contient)
    List<Produit> findByNomContainingIgnoreCase(String nom);

    // Recherche par prix min et max
    List<Produit> findByPrixBetween(Double min, Double max);

    // Produits disponibles
    List<Produit> findByDisponibleTrue();

    // Recherche avec JPQL
    @Query("SELECT p FROM Produit p WHERE p.stock > 0 AND p.disponible = true")
    List<Produit> findProduitsEnStock();

    // Compter par catégorie (si vous ajoutez une catégorie)
    @Query("SELECT COUNT(p) FROM Produit p WHERE p.prix < :prixMax")
    Long countByPrixLessThan(@Param("prixMax") Double prixMax);

    List<Produit> findByStockLessThanEqual(int i);
}