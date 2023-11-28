package com.example.rschir_buysell.controllers.products.seller;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.Image;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.services.products.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/phone")
@PreAuthorize("hasAuthority('ROLE_SELLER') or hasAuthority('ROLE_ADMIN')")
public class PhoneSellerController {
    private final PhoneService phoneService;

    @GetMapping("/create")
    public String createPhonePage(Model model, Principal principal) {
        model.addAttribute("user", phoneService.getClientByPrincipal(principal));
        return "products/phone/phoneCreator";
    }

    @PostMapping("/create")
    public String createPhone(@RequestParam("file1") MultipartFile file1, Phone phone, Principal principal, Model model) throws IOException {
        String st = phoneService.createPhone(principal, phone, file1);
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
        if (phoneService.getClientByPrincipal(principal).getId() != phoneService.getPhoneById(id).getSeller().getId())
            return "redirect:/";
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
    public String updatePhone(@RequestParam("file1") MultipartFile file1, @PathVariable("id") Long id, Phone phone, Model model, Principal principal) throws IOException {
        if (phoneService.getClientByPrincipal(principal).getId() != phoneService.getPhoneById(id).getSeller().getId())
            return "redirect:/";
        String st = phoneService.updatePhone(id, phone, file1);
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
        if (client.getId() != phoneService.getPhoneById(id).getSeller().getId())
            return "redirect:/";
        phoneService.deletePhone(id, client);
        return "redirect:/";
    }
}
