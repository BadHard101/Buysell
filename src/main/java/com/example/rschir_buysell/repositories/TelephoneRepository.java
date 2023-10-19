package com.example.rschir_buysell.repositories;

import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.models.products.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {
}
