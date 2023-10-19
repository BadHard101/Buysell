package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.Product;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.repositories.ProductRepository;
import com.example.rschir_buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/typeSelect")
    public String productTypeSelect(Model model, Principal principal) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("user", productService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "/products/selling/productTypeSelect";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, @AuthenticationPrincipal Client client) {
        if(client.getId() == productRepository.getById(id).getClient().getId()) {
            productRepository.delete(productRepository.getById(id));
        }
        return "redirect:/";
    }
}
