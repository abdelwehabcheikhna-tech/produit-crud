package com.produit.produit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProduitApplication {

    private static String[] args;

    public static void main(String[] args) {
        ProduitApplication.args = args;
        SpringApplication.run(ProduitApplication.class, args);
	}

}
