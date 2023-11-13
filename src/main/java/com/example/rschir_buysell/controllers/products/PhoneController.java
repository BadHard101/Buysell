package com.example.rschir_buysell.controllers.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.services.products.PhoneService;
import lombok.RequiredArgsConstructor;
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
public class PhoneController {
    private final PhoneService phoneService;

    @GetMapping("/selling")
    public String showPhones(Model model, Principal principal) {
        List<Phone> phones = phoneService.getAllPhones();
        model.addAttribute("phones", phones);
        model.addAttribute("user", phoneService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/phone/phones";
    }

    @GetMapping("/create")
    public String createPhonePage(Model model, Principal principal) {
        model.addAttribute("user", phoneService.getClientByPrincipal(principal));
        return "products/phone/phoneCreator";
    }

    @PostMapping("/create")
    public String createPhone(Phone phone, Principal principal, Model model) {
        phone.setProductType(ProductType.Phone);
        String st = phoneService.createPhone(principal, phone);
        if (st.equals("Success")) {
            return "redirect:/phone/selling";
        } else {
            model.addAttribute("user", phoneService.getClientByPrincipal(principal));
            model.addAttribute("errorMessage", st);
            return "products/phone/phoneCreator";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPhoneForm(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("user", phoneService.getClientByPrincipal(principal));
        Phone phone = phoneService.getPhoneById(id);
        model.addAttribute("phone", phone);

        int capacity = phone.getBatteryCapacity();
        String formattedCapacity = Integer.toString(capacity).replace(" ", "");
        model.addAttribute("formattedCapacity", formattedCapacity);

        double price = phone.getPrice();
        String formattedPrice = Double.toString(price).replace(" ", "");
        model.addAttribute("formattedPrice", formattedPrice);

        return "products/phone/phoneEditor";
    }

    @PostMapping("/edit/{id}")
    public String updatePhone(@PathVariable("id") Long id, Phone phone, Model model, Principal principal) {
        String st = phoneService.updatePhone(id, phone);
        if (st.equals("Success")) {
            return "redirect:/phone/selling";
        } else {
            model.addAttribute("user", phoneService.getClientByPrincipal(principal));
            model.addAttribute("phone", phone);

            Phone orig = phoneService.getPhoneById(id);

            int capacity = orig.getBatteryCapacity();
            String formattedCapacity = Integer.toString(capacity).replace(" ", "");
            model.addAttribute("formattedCapacity", formattedCapacity);

            double price = orig.getPrice();
            String formattedPrice = Double.toString(price).replace(" ", "");
            model.addAttribute("formattedPrice", formattedPrice);

            model.addAttribute("errorMessage", st);
            return "products/phone/phoneEditor";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePhone(@PathVariable Long id, @AuthenticationPrincipal Client client) {
        phoneService.deletePhone(id, client);
        return "redirect:/";
    }
}
