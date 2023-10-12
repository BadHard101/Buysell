package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    //private final ProductService productService;

    @GetMapping("/")
    public String products() {
        return "productsOnSell";
    }

    /*@PostMapping("")
    public String addProduct() {

    }

    @PatchMapping("")
    public String updateProduct() {

    }

    @DeleteMapping("")
    public String updateProduct() {

    }*/

}
