package com.example.rschir_buysell.controllers.products.user;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.models.products.WashingMachine;
import com.example.rschir_buysell.services.products.WashingMachineService;
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
@RequestMapping("/washingMachine")
public class WashingMachineUserController {
    private final WashingMachineService washingMachineService;

    @GetMapping("/selling")
    public String showWashingMachines(Model model, Principal principal) {
        List<WashingMachine> washingMachines = washingMachineService.getAllWashingMachines();
        model.addAttribute("washingMachines", washingMachines);
        model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/washingMachine/washingMachines";
    }

    @GetMapping("/{id}")
    public String getWashingMachinePage(@PathVariable("id") Long id, Principal principal, Model model) {
        Client client = washingMachineService.getClientByPrincipal(principal);
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(id);
        model.addAttribute("user", client);
        model.addAttribute("washingMachine", washingMachine);
        return "products/washingMachine/washingMachinePage";
    }
}
