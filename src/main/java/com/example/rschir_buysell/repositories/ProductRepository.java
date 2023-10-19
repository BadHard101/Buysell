package com.example.rschir_buysell.repositories;

import com.example.rschir_buysell.models.Product;
import com.example.rschir_buysell.models.products.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
