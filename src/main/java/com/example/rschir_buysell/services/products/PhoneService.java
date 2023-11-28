package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.Image;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.ImageRepository;
import com.example.rschir_buysell.repositories.products.PhoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final ClientRepository clientRepository;
    private final ImageRepository imageRepository;

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    private String validation(Phone phone) {
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
        ) return "Success";
        else {
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
        return "Error";
    }

    public String createPhone(Principal principal, Phone phone, MultipartFile file1) throws IOException {
        phone.setProductType(ProductType.Phone);

        String validation = validation(phone);
        if (validation.equals("Success")) {
            Image image1;
            if (file1.getSize() != 0) {
                image1 = toImageEntity(file1);
                image1.setPreviewImage(true);
                imageRepository.save(image1);
                phone.setPreviewImageId(image1.getId());
                phone.addImageToProduct(image1);
            }
            phone.setSeller(getClientByPrincipal(principal));
            phoneRepository.save(phone);
        }
        return validation;
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public Phone getPhoneById(Long id) {
        return phoneRepository.getById(id);
    }

    public String updatePhone(Long id, Phone phone, MultipartFile file1) throws IOException {
        String validation = validation(phone);
        if (validation.equals("Success")) {
            Phone original = phoneRepository.getById(id);

            Image image1;
            if (file1.getSize() != 0) {
                if (original.hasPreview()) {
                    imageRepository.deleteById(original.getPreviewImageId());
                }
                image1 = toImageEntity(file1);
                image1.setPreviewImage(true);
                imageRepository.save(image1);
                original.setPreviewImageId(image1.getId());
                original.addImageToProduct(image1);
            }

            original.setName(phone.getName());
            original.setPrice(phone.getPrice());
            // local variables
            original.setManufacturer(phone.getManufacturer());
            original.setBatteryCapacity(phone.getBatteryCapacity());
            phoneRepository.save(original);
        }
        return validation;
    }

    public void deletePhone(Long id, Client client) {
        if (client.getId() == phoneRepository.getById(id).getSeller().getId()) {
            phoneRepository.delete(phoneRepository.getById(id));
        }
    }
}
