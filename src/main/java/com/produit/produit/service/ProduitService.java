package com.produit.produit.service;

import com.produit.produit.entity.Produit;
import org.springframework.stereotype.Service;

import  java.util.ArrayList;
import java.util.List;

public interface ProduitService {
    Produit creerProduit(Produit produit);

    List<Produit> lire();

    Produit modifierProduit(Long id, Produit produit);
    String supprimerProduit(Long id);

    Produit getProduitById(Long id);


}
