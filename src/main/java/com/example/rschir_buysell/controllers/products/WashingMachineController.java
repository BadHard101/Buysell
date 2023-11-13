package com.example.rschir_buysell.controllers.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.WashingMachine;
import com.example.rschir_buysell.services.products.WashingMachineService;
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
@RequestMapping("/washingMachine")
public class WashingMachineController {
    private final WashingMachineService washingMachineService;

    @GetMapping("/selling")
    public String showWashingMachines(Model model, Principal principal) {
        List<WashingMachine> washingMachines = washingMachineService.getAllWashingMachines();
        model.addAttribute("washingMachines", washingMachines);
        model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/washingMachine/washingMachines";
    }

    @GetMapping("/create")
    public String createWashingMachinePage(Model model, Principal principal) {
        model.addAttribute("user", washingMachineService.getClientByPrincipal(principal));
        return "products/washingMachine/washingMachineCreator";
    }

    @PostMapping("/create")
    public String createWashingMachine(WashingMachine washingMachine, Principal principal, Model model) {
        washingMachine.setProductType(ProductType.WashingMachine);
        String st = washingMachineService.createWashingMachine(principal, washingMachine);
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
    public String updateWashingMachine(@PathVariable("id") Long id, WashingMachine washingMachine, Model model, Principal principal) {
        String st = washingMachineService.updateWashingMachine(id, washingMachine);
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
        washingMachineService.deleteWashingMachine(id, client);
        return "redirect:/";
    }
}
