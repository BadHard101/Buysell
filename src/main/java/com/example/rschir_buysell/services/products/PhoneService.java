package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.products.PhoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final ClientRepository clientRepository;

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public String createPhone(Principal principal, Phone phone) {
        if (
                phone.getPrice() != null &&
                        phone.getPrice() > 0 &&
                        phone.getName() != null &&
                        !phone.getName().isEmpty() &&
                        // local variables
                        phone.getManufacturer() != null &&
                        !phone.getManufacturer().isEmpty() &&
                        phone.getBatteryCapacity() != null &&
                        phone.getBatteryCapacity() >= 0
        ) {
            phone.setClient(getClientByPrincipal(principal));
            phoneRepository.save(phone);
        } else {
            if (phone.getPrice() == null) {
                return "Укажите цену!";
            } else if (phone.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (phone.getName() == null || phone.getName().isEmpty()) {
                return "Напишите название телефона!";
            } else if (phone.getManufacturer() == null || phone.getManufacturer().isEmpty()) {
                return "Укажите производителя!";
            } else if (phone.getBatteryCapacity() == null) {
                return "Укажите вместимость батареи!";
            } else if (phone.getBatteryCapacity() < 0) {
                return "Укажите корректную вместимость батареи!";
            }
        }
        return "Success";
    }

    public Phone getPhoneById(Long id) {
        return phoneRepository.getById(id);
    }

    public String updatePhone(Long id, Phone phone) {
        if (
                phone.getPrice() != null &&
                        phone.getPrice() > 0 &&
                        phone.getName() != null &&
                        !phone.getName().isEmpty() &&
                        // local variables
                        phone.getManufacturer() != null &&
                        !phone.getManufacturer().isEmpty() &&
                        phone.getBatteryCapacity() != null &&
                        phone.getBatteryCapacity() >= 0
        ) {
            Phone original = phoneRepository.getById(id);
            original.setName(phone.getName());
            original.setPrice(phone.getPrice());
            // local variables
            original.setManufacturer(phone.getManufacturer());
            original.setBatteryCapacity(phone.getBatteryCapacity());
            phoneRepository.save(original);
        } else {
            if (phone.getPrice() == null) {
                return "Укажите цену!";
            } else if (phone.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (phone.getName() == null || phone.getName().isEmpty()) {
                return "Напишите название телефона!";
            } else if (phone.getManufacturer() == null || phone.getManufacturer().isEmpty()) {
                return "Укажите производителя!";
            } else if (phone.getBatteryCapacity() == null) {
                return "Укажите вместимость батареи!";
            } else if (phone.getBatteryCapacity() < 0) {
                return "Укажите корректную вместимость батареи!";
            }
        }

        return "Success";
    }

    public void deletePhone(Long id, Client client) {
        if (client.getId() == phoneRepository.getById(id).getClient().getId()) {
            phoneRepository.delete(phoneRepository.getById(id));
        }
    }
}
