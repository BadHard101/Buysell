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

    @GetMapping("")
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
    public String createProduct(Product product, Principal principal, Model model) {
        if (product.getProductType() != ProductType.Тип && product.getPrice() != null && product.getPrice() > 0 &&
                product.getName() != null && !product.getName().isEmpty()) {
            productService.createProduct(principal, product);
            return "redirect:/products/";
        } else {
            model.addAttribute("user", productService.getClientByPrincipal(principal));
            model.addAttribute("types", ProductType.values());
            if (product.getProductType() == ProductType.Тип) {
                model.addAttribute("errorMessage", "Выберете тип продукта!");
            } else if (product.getPrice() == null) {
                model.addAttribute("errorMessage", "Укажите цену!");
            } else if (product.getPrice() <= 0) {
                model.addAttribute("errorMessage", "Укажите корректную цену!");
            } else if (product.getName() == null || product.getName().isEmpty()) {
                model.addAttribute("errorMessage", "Напишите имя!");
            }
            return "user/productCreator";
        }
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("user", productService.getClientByPrincipal(principal));
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("types", ProductType.values());

        // Получите цену товара (например, из базы данных)
        double price = product.getPrice();
        // Удалите пробелы из форматированной цены
        String formattedPrice = Double.toString(price).replace(" ", "");
        // Передайте форматированную цену в модель
        model.addAttribute("formattedPrice", formattedPrice);

        return "user/productEditor";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long id, Product product, Model model, Principal principal) {
        if (product.getPrice() != null && product.getPrice() > 0 &&
                product.getName() != null && !product.getName().isEmpty()) {
            productService.updateProduct(id, product);
            return "redirect:/products/";
        } else {
            model.addAttribute("user", productService.getClientByPrincipal(principal));
            model.addAttribute("types", ProductType.values());
            if (product.getPrice() == null) {
                model.addAttribute("errorMessage", "Укажите цену!");
            } else if (product.getPrice() <= 0) {
                model.addAttribute("errorMessage", "Укажите корректную цену!");
            } else if (product.getName() == null || product.getName().isEmpty()) {
                model.addAttribute("errorMessage", "Напишите имя!");
            }

            product = productService.getProductById(id);
            // Получите цену товара (например, из базы данных)
            double price = product.getPrice();
            // Удалите пробелы из форматированной цены
            String formattedPrice = Double.toString(price).replace(" ", "");
            // Передайте форматированную цену в модель
            model.addAttribute("formattedPrice", formattedPrice);

            return "user/productEditor";
        }
    }

    @GetMapping("/delete/{id}")
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/";
    }
}
