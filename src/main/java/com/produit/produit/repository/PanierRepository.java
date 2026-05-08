package com.produit.produit.repository;
import com.produit.produit.entity.Panier;
import com.produit.produit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    Optional<Panier> findByUser(User user);
    Optional<Panier> findByUserId(Long userId);
}

