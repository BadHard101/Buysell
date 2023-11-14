package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.ShoppingCart;
import com.example.rschir_buysell.models.products.Phone;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.ShoppingCartRepository;
import com.example.rschir_buysell.repositories.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    private String validation(Phone product) {
        if (
                product.getPrice() != null &&
                        product.getPrice() > 0 &&
                        product.getName() != null &&
                        !product.getName().isEmpty() &&
                        // local variables
                        product.getManufacturer() != null &&
                        !product.getManufacturer().isEmpty() &&
                        product.getBatteryCapacity() != null &&
                        product.getBatteryCapacity() >= 0
        ) return "Success";
        else {
            if (product.getPrice() == null) {
                return "Укажите цену!";
            } else if (product.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (product.getName() == null || product.getName().isEmpty()) {
                return "Напишите название телефона!";
            } else if (product.getManufacturer() == null || product.getManufacturer().isEmpty()) {
                return "Укажите производителя!";
            } else if (product.getBatteryCapacity() == null) {
                return "Укажите вместимость батареи!";
            } else if (product.getBatteryCapacity() < 0) {
                return "Укажите корректную вместимость батареи!";
            }
        }
        return "Error";
    }

    private String validation(Product product) {
        if (
                product.getPrice() != null &&
                        product.getPrice() > 0 &&
                        product.getName() != null &&
                        !product.getName().isEmpty()
        ) return "Success";
        else {
            if (product.getPrice() == null) {
                return "Укажите цену!";
            } else if (product.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (product.getName() == null || product.getName().isEmpty()) {
                return "Напишите имя!";
            }
        }
        return "Error";
    }

    public String createProduct(Principal principal, Product product) {
        String validation = validation(product);
        if (validation.equals("Success")) {
            product.setSeller(getClientByPrincipal(principal));
            productRepository.save(product);
        }
        return validation;
    }

    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    public String updateProduct(Long id, Product product) {
        String validation = validation(product);
        if (validation.equals("Success")) {
            Product original = productRepository.getById(id);
            original.setName(product.getName());
            original.setPrice(product.getPrice());
            original.setProductType(product.getProductType());
            productRepository.save(original);
        }
        return validation;

    }

    public void deleteProduct(Long id, Client client) {
        if (client.getId() == productRepository.getById(id).getSeller().getId()) {
            productRepository.delete(productRepository.getById(id));
        }
    }

    public ShoppingCart getOrCreateShoppingCartByClient(Client client) {
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByClientAndActive(client, true);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setClient(client);
            cart.setActive(true);
            shoppingCartRepository.save(cart);
        }
        return cart;
    }


    public String addProductToCart(Long id, Client client) {
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByClientAndActive(client, true);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setClient(client);
            cart.setActive(true);
        }

        Product product = getProductById(id);
        if (product.getQuantity() > cart.getItems().get(product))
            cart.addItem(getProductById(id));
        else return "Вы не можете добавить этого товара больше";

        shoppingCartRepository.save(cart);
        return "Success";
    }

    public void removeProductToCart(Long id, Client client) {
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByClientAndActive(client, true);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setClient(client);
            cart.setActive(true);
        }

        cart.removeItem(getProductById(id));

        shoppingCartRepository.save(cart);
    }

    /*public String checkoutShoppingCart(Client client) {
        ShoppingCart cart = getOrCreateShoppingCartByClient(client);
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product.getQuantity() < quantity) {

                return "";
            }
        }

    }*/
}
