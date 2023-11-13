package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.models.products.WashingMachine;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.products.WashingMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WashingMachineService {
    private final WashingMachineRepository washingMachineRepository;
    private final ClientRepository clientRepository;

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    public List<WashingMachine> getAllWashingMachines() {
        return washingMachineRepository.findAll();
    }

    private String validation(WashingMachine washingMachine) {
        if (
                washingMachine.getPrice() != null &&
                        washingMachine.getPrice() > 0 &&
                        washingMachine.getName() != null &&
                        !washingMachine.getName().isEmpty() &&
                        // local variables
                        washingMachine.getManufacturer() != null &&
                        !washingMachine.getManufacturer().isEmpty() &&
                        washingMachine.getTankCapacity() != null &&
                        washingMachine.getTankCapacity() > 0
        ) return "Success";
        else {
            if (washingMachine.getPrice() == null) {
                return "Укажите цену!";
            } else if (washingMachine.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (washingMachine.getName() == null || washingMachine.getName().isEmpty()) {
                return "Напишите название стиральной машины!";
            } else if (washingMachine.getManufacturer() == null || washingMachine.getManufacturer().isEmpty()) {
                return "Укажите производителя!";
            } else if (washingMachine.getTankCapacity() == null) {
                return "Укажите вместимость бака!";
            } else if (washingMachine.getTankCapacity() < 0) {
                return "Укажите корректную вместимость бака!";
            }
        }
        return "Error";
    }

    public String createWashingMachine(Principal principal, WashingMachine washingMachine) {
        String validation = validation(washingMachine);
        if (validation.equals("Success")) {
            washingMachine.setSeller(getClientByPrincipal(principal));
            washingMachineRepository.save(washingMachine);
        }
        return validation;
    }

    public WashingMachine getWashingMachineById(Long id) {
        return washingMachineRepository.getById(id);
    }

    public String updateWashingMachine(Long id, WashingMachine washingMachine) {
        String validation = validation(washingMachine);
        if (validation.equals("Success")) {
            WashingMachine original = washingMachineRepository.getById(id);
            original.setName(washingMachine.getName());
            original.setPrice(washingMachine.getPrice());
            // local variables
            original.setManufacturer(washingMachine.getManufacturer());
            original.setTankCapacity(washingMachine.getTankCapacity());
            washingMachineRepository.save(original);
        }
        return validation;
    }

    public void deleteWashingMachine(Long id, Client client) {
        if (client.getId() == washingMachineRepository.getById(id).getSeller().getId()) {
            washingMachineRepository.delete(washingMachineRepository.getById(id));
        }
    }
}
