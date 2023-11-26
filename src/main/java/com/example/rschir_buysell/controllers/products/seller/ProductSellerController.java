package com.example.rschir_buysell.controllers.products.seller;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.ShoppingCart;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@PreAuthorize("hasAuthority('ROLE_SELLER') or hasAuthority('ROLE_ADMIN')")
public class ProductSellerController {

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
}
