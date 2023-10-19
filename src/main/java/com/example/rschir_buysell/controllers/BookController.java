package com.example.rschir_buysell.controllers;

import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class BookController {
    private final BookService bookService;

    @GetMapping("/selling")
    public String showBooks(Model model, Principal principal) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("user", bookService.getClientByPrincipal(principal));
        model.addAttribute("types", ProductType.values());
        return "products/book/books";
    }

    @GetMapping("/create")
    public String createBookPage(Model model, Principal principal) {
        model.addAttribute("user", bookService.getClientByPrincipal(principal));
        return "products/book/bookCreator";
    }

    @PostMapping("/create")
    public String createBook(Book book, Principal principal, Model model) {
        book.setProductType(ProductType.Books);
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

    /*@GetMapping("/delete/{id}")
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        if (bookService.getBookById(id).getClient().getEmail().equals(email))
            bookService.deleteBook(id);
        return "redirect:/book/selling";
    }*/

}
