package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.Product;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(Model model, Principal principal) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("user", productService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "user/productsOnSell";
    }

    @GetMapping("/create")
    public String createProductPage(Model model, Principal principal) {
        model.addAttribute("user", productService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "user/productCreator";
    }

    @PostMapping("/create")
    public String createProduct(Product product, Principal principal) {
        productService.createProduct(principal, product);
        return "redirect:/products/";
    }

    /*@GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "user/editProduct";
    }

    @PatchMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/products/";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/";
    }*/
}
