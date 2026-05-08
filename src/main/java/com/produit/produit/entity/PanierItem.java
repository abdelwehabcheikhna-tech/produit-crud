package com.produit.produit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "panier_items")
public class PanierItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id")
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private Integer quantite;

    private double prixUnitaire;

    private double sousTotal;

    // Constructeurs
    public PanierItem() {}

    public PanierItem(Produit produit, Integer quantite) {
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = produit.getPrix();
        this.sousTotal = produit.getPrix();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
        this.sousTotal = this.prixUnitaire;
    }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getSousTotal() { return sousTotal; }
    public void setSousTotal(double sousTotal) { this.sousTotal = sousTotal; }
}
