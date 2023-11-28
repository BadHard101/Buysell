package com.example.rschir_buysell.services.products;

import com.example.rschir_buysell.models.Client;
import com.example.rschir_buysell.models.Image;
import com.example.rschir_buysell.models.enums.ProductType;
import com.example.rschir_buysell.models.products.Book;
import com.example.rschir_buysell.repositories.ClientRepository;
import com.example.rschir_buysell.repositories.ImageRepository;
import com.example.rschir_buysell.repositories.products.BookRepository;
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
public class BookService {
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final ImageRepository imageRepository;

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

    public String createBook(Principal principal, Book book, MultipartFile file1) throws IOException {
        book.setProductType(ProductType.Book);

        String validation = validation(book);
        if (validation.equals("Success")) {
            Image image1;
            if (file1.getSize() != 0) {
                image1 = toImageEntity(file1);
                image1.setPreviewImage(true);
                imageRepository.save(image1);
                book.setPreviewImageId(image1.getId());
                book.addImageToProduct(image1);
            }

            book.setSeller(getClientByPrincipal(principal));
            bookRepository.save(book);
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

    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    public String updateBook(Long id, Book book, MultipartFile file1) throws IOException {
        String validation = validation(book);
        if (validation.equals("Success")) {
            Book original = bookRepository.getById(id);

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

            original.setName(book.getName());
            original.setPrice(book.getPrice());
            original.setQuantity(book.getQuantity());
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
