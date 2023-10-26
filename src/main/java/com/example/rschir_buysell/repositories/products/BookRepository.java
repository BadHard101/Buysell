package com.example.rschir_buysell.repositories.products;

import com.example.rschir_buysell.models.products.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
