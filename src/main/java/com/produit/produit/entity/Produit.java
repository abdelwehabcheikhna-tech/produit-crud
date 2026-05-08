package com.produit.produit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(unique = true)
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    private double prix;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stock;

    private String imageUrl;

    private Boolean disponible = true;

    // Constructeurs
    public Produit() {}

    public Produit(String nom, String description, double prix, Integer stock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}