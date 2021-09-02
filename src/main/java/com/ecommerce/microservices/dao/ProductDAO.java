package com.ecommerce.microservices.dao;

import com.ecommerce.microservices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    Product findById(int id);

    List<Product> findByPrixGreaterThan(int prixLimit);

    List<Product> findByNomLike(String recherche);

    void deleteById(int id);
}
