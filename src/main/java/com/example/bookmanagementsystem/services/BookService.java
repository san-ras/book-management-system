package com.example.bookmanagementsystem.services;


import com.example.bookmanagementsystem.dto.BookDTO;
import com.example.bookmanagementsystem.entities.Book;
import com.example.bookmanagementsystem.entities.Category;
import com.example.bookmanagementsystem.exceptions.InvalidBookDetailsException;
import com.example.bookmanagementsystem.repositories.BookRepository;
import com.example.bookmanagementsystem.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public BookDTO addBook(BookDTO newBookDTO) {
        if (bookDetailsAreNotCorrect(newBookDTO)) {
            throw new InvalidBookDetailsException("Invalid book details");
        }
        Book book = convertToEntity(newBookDTO);
        bookRepository.save(book);
        return convertToDTO(book);
    }

    public BookDTO updateBook(BookDTO updatedBookDTO, int id) {
        if (bookDetailsAreNotCorrect(updatedBookDTO)) {
            throw new InvalidBookDetailsException("Invalid book details");
        }
        Book bookWithUpdatedDetails = convertToEntity(updatedBookDTO);
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with specified id does not exist"));
        bookToUpdate.setAuthor(bookWithUpdatedDetails.getAuthor());
        bookToUpdate.setTitle(bookWithUpdatedDetails.getTitle());
        bookToUpdate.setCategory(getBookCategory(updatedBookDTO));
        bookToUpdate.setIsbn(bookWithUpdatedDetails.getIsbn());
        bookToUpdate.setPages(bookWithUpdatedDetails.getPages());
        bookToUpdate.setCover(bookWithUpdatedDetails.getCover());
        bookToUpdate.setDescription(bookWithUpdatedDetails.getDescription());

        bookRepository.save(bookToUpdate);

        return convertToDTO(bookToUpdate);
    }

    private Category getBookCategory(BookDTO updatedBookDTO) {
        return categoryRepository.findByName(updatedBookDTO.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(updatedBookDTO.getCategoryName());
                    return categoryRepository.save(newCategory);
                });
    }

    public void deleteBook(int id) {
        Book bookToDelete = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with specified id does not exist"));
        bookRepository.delete(bookToDelete);
    }


    private boolean bookDetailsAreNotCorrect(BookDTO bookDTO) {
        return bookDTO == null || bookDTO.getAuthor() == null || bookDTO.getTitle() == null ||
                bookDTO.getCategoryName() == null || bookDTO.getAuthor().isEmpty() ||
                bookDTO.getTitle().isEmpty() || bookDTO.getIsbn().isEmpty() ||
                bookDTO.getCategoryName().isEmpty();
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setCategoryName(book.getCategory().getName());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setCover(book.getCover());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPages(book.getPages());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setCategory(getBookCategory(bookDTO));
        book.setIsbn(bookDTO.getIsbn());
        book.setPages(bookDTO.getPages());
        book.setCover(bookDTO.getCover());
        book.setDescription(bookDTO.getDescription());
        return book;
    }

    public List<BookDTO> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream().map(this::convertToDTO).toList();
    }

    public List<BookDTO> getBooksByCategoryId(Integer id) {
        List<Book> books = bookRepository.findByCategoryCategoryId(id);
        return books.stream().map(this::convertToDTO).toList();

    }

}


