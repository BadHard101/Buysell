package com.example.rschir_buysell.repositories.products;

import com.example.rschir_buysell.models.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
