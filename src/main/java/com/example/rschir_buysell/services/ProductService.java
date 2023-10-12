package com.example.rschir_buysell.services;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.Product;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    public void createProduct(Principal principal, Product product) {
        product.setClient(getClientByPrincipal(principal));
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    public void updateProduct(Long id, Product product) {
        Product original = productRepository.getById(id);
        original.setName(product.getName());
        original.setPrice(product.getPrice());
        original.setProductType(product.getProductType());
        productRepository.save(original);
    }
}
