package com.produit.produit.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acheteur;

    @ElementCollection
    @CollectionTable(name = "commande_produits",
            joinColumns = @JoinColumn(name = "commande_id"))
    private List<Long> produitIds;  // Stocker les IDs au lieu des objets

    @Transient  // Ne pas persister directement
    private List<Produit> produits;

    private double total;

    public Commande() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public List<Long> getProduitIds() {
        return produitIds;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public double getTotal() {
        return total;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public void setProduitIds(List<Long> produitIds) {
        this.produitIds = produitIds;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
        // Convertir en IDs pour la persistance
        if (produits != null) {
            this.produitIds = produits.stream()
                    .map(Produit::getId)
                    .collect(java.util.stream.Collectors.toList());
        }
    }

    public void setTotal(double total) {
        this.total = total;
    }
}