package com.example.bookmanagementsystem.controllers;


import com.example.bookmanagementsystem.dto.BookDTO;
import com.example.bookmanagementsystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }


    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO newBookDTO) {
        BookDTO book = bookService.addBook(newBookDTO);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO updatedBookDTO, @PathVariable int id) {
        BookDTO bookDTO = bookService.updateBook(updatedBookDTO, id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.getBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/search/category/{id}")
    public ResponseEntity<List<BookDTO>> getBooksByCategoryId(@PathVariable Integer id) {
        List<BookDTO> books = bookService.getBooksByCategoryId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
