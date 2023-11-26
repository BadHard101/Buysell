package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    /*@GetMapping("/panel")
    public String showPanel() {
        return "admin/panel";
    }*/

    @GetMapping("/user/{id}")
    public String showUserInfo(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal Client user) {
        Client client = clientRepository.getById(id);
        List<Product> products = productRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        model.addAttribute("products", products);
        return "user/userPage";
    }
}
