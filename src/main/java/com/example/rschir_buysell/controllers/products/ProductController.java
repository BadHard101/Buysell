package com.example.rschir_buysell.controllers.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.repositories.products.ProductRepository;
import com.example.rschir_buysell.services.products.ProductService;
import lombok.RequiredArgsConstructor;
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
        productService.deleteProduct(id, client);
        return "redirect:/";
    }

    @GetMapping("/addToCart/{id}")
    public String addProductToCart(@PathVariable Long id, Principal principal) {
        productService.addProductToCart(id, principal);
        return "redirect:/";
    }
}
