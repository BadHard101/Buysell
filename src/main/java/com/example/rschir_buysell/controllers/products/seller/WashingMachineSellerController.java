package com.example.rschir_buysell.controllers.products.seller;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.WashingMachine;
import com.example.rschir_buysell.services.products.WashingMachineService;
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
@RequestMapping("/washingMachine")
@PreAuthorize("hasAuthority('ROLE_SELLER') or hasAuthority('ROLE_ADMIN')")
public class WashingMachineSellerController {
    private final WashingMachineService washingMachineService;

    @GetMapping("/create")
    public String createWashingMachinePage(Model model, Principal principal) {
        model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
        return "products/washingMachine/washingMachineCreator";
    }

    @PostMapping("/create")
    public String createWashingMachine(@RequestParam("file1") MultipartFile file1, WashingMachine washingMachine, Principal principal, Model model) throws IOException {
        String st = washingMachineService.createWashingMachine(principal, washingMachine, file1);
        if (st.equals("Success")) {
            return "redirect:/washingMachine/selling";
        } else {
            model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
            model.addAttribute("errorMessage", st);
            return "products/washingMachine/washingMachineCreator";
        }
    }

    @GetMapping("/edit/{id}")
    public String editWashingMachineForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (washingMachineService.getClientByPrincipal(principal).getId() !=
                washingMachineService.getWashingMachineById(id).getSeller().getId())
            return "redirect:/";
        model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(id);
        model.addAttribute("washingMachine", washingMachine);

        double capacity = washingMachine.getTankCapacity();
        String formattedCapacity = Double.toString(capacity).replace(" ", "");
        model.addAttribute("formattedCapacity", formattedCapacity);

        double price = washingMachine.getPrice();
        String formattedPrice = Double.toString(price).replace(" ", "");
        model.addAttribute("formattedPrice", formattedPrice);

        return "products/washingMachine/washingMachineEditor";
    }

    @PostMapping("/edit/{id}")
    public String updateWashingMachine(@RequestParam("file1") MultipartFile file1, @PathVariable("id") Long id,
                                       WashingMachine washingMachine, Model model, Principal principal) throws IOException {
        if (washingMachineService.getClientByPrincipal(principal).getId() !=
                washingMachineService.getWashingMachineById(id).getSeller().getId())
            return "redirect:/";
        String st = washingMachineService.updateWashingMachine(id, washingMachine, file1);
        if (st.equals("Success")) {
            return "redirect:/washingMachine/selling";
        } else {
            model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
            model.addAttribute("washingMachine", washingMachine);

            WashingMachine orig = washingMachineService.getWashingMachineById(id);

            double capacity = orig.getTankCapacity();
            String formattedCapacity = Double.toString(capacity).replace(" ", "");
            model.addAttribute("formattedCapacity", formattedCapacity);

            double price = orig.getPrice();
            String formattedPrice = Double.toString(price).replace(" ", "");
            model.addAttribute("formattedPrice", formattedPrice);

            model.addAttribute("errorMessage", st);
            return "products/washingMachine/washingMachineEditor";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteWashingMachine(@PathVariable Long id, @AuthenticationPrincipal Client client) {
        if (client.getId() != washingMachineService.getWashingMachineById(id).getSeller().getId())
            return "redirect:/";
        washingMachineService.deleteWashingMachine(id, client);
        return "redirect:/";
    }
}
