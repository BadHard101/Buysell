package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.repositories.products.BookRepository;
import com.example.rschir_buysell.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }



    private String validation(Book book) {
        if (
                book.getPrice() != null &&
                book.getPrice() > 0 &&
                book.getName() != null &&
                !book.getName().isEmpty() &&
                // local variables
                book.getAuthor() != null &&
                !book.getAuthor().isEmpty()
        ) return "Success";
        else {
            if (book.getPrice() == null) {
                return "Укажите цену!";
            } else if (book.getPrice() <= 0) {
                return "Укажите корректную цену!";
            } else if (book.getName() == null || book.getName().isEmpty()) {
                return "Напишите название книги!";
            } else if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
                return "Напишите имя автора!";
            }
        }
        return "Error";
    }

    public String createBook(Principal principal, Book book) {
        String validation = validation(book);
        if (validation.equals("Success")) {
            book.setSeller(getClientByPrincipal(principal));
            bookRepository.save(book);
        }
        return validation;
    }

    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    public String updateBook(Long id, Book book) {
        String validation = validation(book);
        if (validation.equals("Success")) {
            Book original = bookRepository.getById(id);
            original.setName(book.getName());
            original.setPrice(book.getPrice());
            // local variables
            original.setAuthor(book.getAuthor());
            bookRepository.save(original);
        }
        return validation;
    }

    public void deleteBook(Long id, Client client) {
        if (client.getId() == bookRepository.getById(id).getSeller().getId()) {
            bookRepository.delete(bookRepository.getById(id));
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
