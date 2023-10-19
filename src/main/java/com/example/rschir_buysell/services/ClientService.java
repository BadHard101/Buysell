package com.example.rschir_buysell.services;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.Role;
import com.example.rschir_buysell.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public String createClient(Client client) {
        String temp = client.getEmail();
        if (clientRepository.findByEmail(temp) != null)
            return "email";
        temp = client.getLogin();
        System.out.println(clientRepository.findByLogin(temp));
        if (clientRepository.findByLogin(temp) != null)
            return "login";
        client.setActive(true);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.getRoles().add(Role.ROLE_USER);
        clientRepository.save(client);
        return "";
    }

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

}
