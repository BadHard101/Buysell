package com.example.rschir_buysell.repositories;

import com.example.rschir_buysell.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByLogin(String login);
}
