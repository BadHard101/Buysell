package com.example.rschir_buysell.controllers.products.seller;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.services.products.BookService;
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
@RequestMapping("/book")
@PreAuthorize("hasAuthority('ROLE_SELLER') or hasAuthority('ROLE_ADMIN')")
public class BookSellerController {
    private final BookService bookService;

    @GetMapping("/create")
    public String createBookPage(Model model, Principal principal) {
        model.addAttribute("user", bookService.getClientByPrincipal(principal));
        return "products/book/bookCreator";
    }

    @PostMapping("/create")
    public String createBook(Book book, Principal principal, Model model) {
        book.setProductType(ProductType.Book);
        String st = bookService.createBook(principal, book);
        if (st.equals("Success")) {
            return "redirect:/book/selling";
        } else {
            model.addAttribute("user", bookService.getClientByPrincipal(principal));
            model.addAttribute("errorMessage", st);
            return "products/book/bookCreator";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (bookService.getClientByPrincipal(principal).getId() != bookService.getBookById(id).getSeller().getId())
            return "redirect:/";
        model.addAttribute("user", bookService.getClientByPrincipal(principal));
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);

        double price = book.getPrice();
        String formattedPrice = Double.toString(price).replace(" ", "");
        model.addAttribute("formattedPrice", formattedPrice);

        return "products/book/bookEditor";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, Book book, Model model, Principal principal) {
        if (bookService.getClientByPrincipal(principal).getId() != bookService.getBookById(id).getSeller().getId())
            return "redirect:/";
        String st = bookService.updateBook(id, book);
        if (st.equals("Success")) {
            return "redirect:/book/selling";
        } else {
            model.addAttribute("user", bookService.getClientByPrincipal(principal));
            model.addAttribute("book", book);

            Book orig = bookService.getBookById(id);
            double price = orig.getPrice();
            String formattedPrice = Double.toString(price).replace(" ", "");
            model.addAttribute("formattedPrice", formattedPrice);

            model.addAttribute("errorMessage", st);
            return "products/book/bookEditor";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, @AuthenticationPrincipal Client client) {
        if (client.getId() != bookService.getBookById(id).getSeller().getId())
            return "redirect:/";
        bookService.deleteBook(id, client);
        return "redirect:/";
    }
}
