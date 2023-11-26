package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.services.ClientService;
import com.example.rschir_buysell.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ProductService productService;

    @GetMapping("/login")
    public String login() {
        return "authorization/login";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", "Введены неверные данные");
        return "authorization/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "authorization/registration";
    }

    @PostMapping("/registration")
    public String createUser(Client client, Model model) {
        String temp = clientService.createClient(client);
        if (!temp.isEmpty()) {
            if (temp.equals("login")) {
                model.addAttribute("errorMessage",
                        "Пользователь с логином: " + client.getLogin() + " уже существует");
            } else if (temp.equals("email")){
                model.addAttribute("errorMessage",
                        "Пользователь с почтой: " + client.getEmail() + " уже существует");
            }
            return "authorization/registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal Client client, Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("user", client);
        return "user/account";
    }

    @GetMapping("")
    public String categories(Model model, Principal principal) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("user", clientService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "user/main";
    }

}
