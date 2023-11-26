package com.example.rschir_buysell.controllers.products.user;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.services.products.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/phone")
public class PhoneUserController {
    private final PhoneService phoneService;

    @GetMapping("/selling")
    public String showPhones(Model model, Principal principal) {
        List<Phone> phones = phoneService.getAllPhones();
        model.addAttribute("phones", phones);
        model.addAttribute("user", phoneService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/phone/phones";
    }

    @GetMapping("/{id}")
    public String getPhonePage(@PathVariable("id") Long id, Principal principal, Model model) {
        Client client = phoneService.getClientByPrincipal(principal);
        Phone phone = phoneService.getPhoneById(id);
        model.addAttribute("user", client);
        model.addAttribute("phone", phone);
        return "products/phone/phonePage";
    }
}
