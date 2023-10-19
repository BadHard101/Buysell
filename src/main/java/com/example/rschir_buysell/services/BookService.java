package com.example.rschir_buysell.services;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.repositories.BookRepository;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public Client getClientByPrincipal(Principal principal) {
        if (principal == null) return new Client();
        return clientRepository.findByEmail(principal.getName());
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public String createBook(Principal principal, Book book) {
        if (
                book.getPrice() != null &&
                        book.getPrice() > 0 &&
                        book.getName() != null &&
                        !book.getName().isEmpty() &&
                        book.getAuthor() != null &&
                        !book.getAuthor().isEmpty()
        ) {
            book.setClient(getClientByPrincipal(principal));
            bookRepository.save(book);
        } else {
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
        return "Success";
    }

    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    public String updateBook(Long id, Book book) {
        if (
                book.getPrice() != null &&
                        book.getPrice() > 0 &&
                        book.getName() != null &&
                        !book.getName().isEmpty() &&
                        book.getAuthor() != null &&
                        !book.getAuthor().isEmpty()
        ) {
            Book original = bookRepository.getById(id);
            original.setName(book.getName());
            original.setPrice(book.getPrice());
            original.setAuthor(book.getAuthor());
            bookRepository.save(original);
        } else {
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

        return "Success";
    }

    @Transactional
    public void deleteBook(Long id) {
        productRepository.deleteById(id);
        productRepository.flush();
    }
}
