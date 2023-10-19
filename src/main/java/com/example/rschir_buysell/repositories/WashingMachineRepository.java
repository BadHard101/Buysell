package com.example.rschir_buysell.repositories;

import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.models.products.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WashingMachineRepository extends JpaRepository<WashingMachine, Long> {
}
