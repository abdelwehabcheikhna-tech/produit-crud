package com.produit.produit.entity;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paniers")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PanierItem> items = new ArrayList<>();

    private Double total = 0.0;

    // Constructeurs
    public Panier(String user) {}

    public Panier(User user) {
        this.user = user;
    }

    public Panier() {

    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<PanierItem> getItems() { return items; }
    public void setItems(List<PanierItem> items) { this.items = items; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}