package com.example.rschir_buysell.controllers.products.user;

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
public class BookUserController {
    private final BookService bookService;

    @GetMapping("/selling")
    public String showBooks(Model model, Principal principal) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("user", bookService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/book/books";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable("id") Long id, Principal principal, Model model) {
        Client client = bookService.getClientByPrincipal(principal);
        Book book = bookService.getBookById(id);
        model.addAttribute("user", client);
        model.addAttribute("book", book);
        return "products/book/bookPage";
    }
}
