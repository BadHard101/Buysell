package com.example.rschir_buysell.controllers.products.user;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.ShoppingCart;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Product;
import com.example.rschir_buysell.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductUserController {

    private final ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addProductToCart(@PathVariable Long id, @AuthenticationPrincipal Client client, Model model) {
        String st = productService.addProductToCart(id, client);
        if (!st.equals("Success")) model.addAttribute("errorMessage", st);
        /*String original = productService.getProductById(id).getProductType().toString();
        String modified = original.substring(0, 1).toLowerCase() + original.substring(1);
        return "redirect:/" + modified + "/selling";*/
        return "redirect:/product/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String showShoppingCart(@AuthenticationPrincipal Client client, Model model) {
        ShoppingCart cart = productService.getOrCreateShoppingCartByClient(client);
        Map<Product, Integer> items = cart.getItems();
        // Преобразование Map в список для сортировки
        List<Map.Entry<Product, Integer>> sortedItems = new ArrayList<>(items.entrySet());
        // Сортировка списка. Пример: сортировка по имени продукта
        sortedItems.sort(Comparator.comparing(entry -> entry.getKey().getPrice()));

        // Теперь, когда у вас есть отсортированный список, добавьте его в модель
        model.addAttribute("user", client);
        model.addAttribute("items", sortedItems); // Используйте отсортированный список здесь
        model.addAttribute("cart", cart); // Используйте отсортированный список здесь

        return "user/shoppingCart";
    }


    @GetMapping("/shoppingCart/addItem/{id}")
    public String addItemToShoppingCart(@PathVariable Long id, @AuthenticationPrincipal Client client, Model model) {
        String st = productService.addProductToCart(id, client);
        if (!st.equals("Success")) model.addAttribute("errorMessage", st);

        ShoppingCart cart = productService.getOrCreateShoppingCartByClient(client);
        Map<Product, Integer> items = cart.getItems();
        // Преобразование Map в список для сортировки
        List<Map.Entry<Product, Integer>> sortedItems = new ArrayList<>(items.entrySet());
        // Сортировка списка. Пример: сортировка по имени продукта
        sortedItems.sort(Comparator.comparing(entry -> entry.getKey().getPrice()));

        // Теперь, когда у вас есть отсортированный список, добавьте его в модель
        model.addAttribute("user", client);
        model.addAttribute("items", sortedItems); // Используйте отсортированный список здесь
        model.addAttribute("cart", cart); // Используйте отсортированный список здесь
        return "user/shoppingCart";
    }

    @GetMapping("/shoppingCart/removeItem/{id}")
    public String removeItemFromShoppingCart(@PathVariable Long id, @AuthenticationPrincipal Client client, Model model) {
        productService.removeProductToCart(id, client);
        return "redirect:/product/shoppingCart";
    }

    @GetMapping("/shoppingCart/checkout")
    public String checkoutShoppingCart(@AuthenticationPrincipal Client client, Model model) {
        String st = productService.checkoutShoppingCart(client);
        if (!st.equals("Success")) model.addAttribute("errorMessage", st);
        else {
            model.addAttribute("successLabel", "Вы успешно оформили заказ!");
            model.addAttribute("user", client);
            return "user/orderPage";
        }

        ShoppingCart cart = productService.getOrCreateShoppingCartByClient(client);
        Map<Product, Integer> items = cart.getItems();
        // Преобразование Map в список для сортировки
        List<Map.Entry<Product, Integer>> sortedItems = new ArrayList<>(items.entrySet());
        // Сортировка списка. Пример: сортировка по имени продукта
        sortedItems.sort(Comparator.comparing(entry -> entry.getKey().getPrice()));

        // Теперь, когда у вас есть отсортированный список, добавьте его в модель
        model.addAttribute("user", client);
        model.addAttribute("items", sortedItems); // Используйте отсортированный список здесь
        model.addAttribute("cart", cart); // Используйте отсортированный список здесь

        return "user/shoppingCart";
    }
}
