package com.bits_wilp.bookexchangeplatform.controllers;

import com.bits_wilp.bookexchangeplatform.dto.BookDTO;
import com.bits_wilp.bookexchangeplatform.entity.Book;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;
import com.bits_wilp.bookexchangeplatform.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    // Create a new book listing
    @PostMapping("/")
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        return new ResponseEntity("Book added", HttpStatus.CREATED);
    }

    // Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return new ResponseEntity(updatedBook, HttpStatus.OK);
    }

    // Delete a book listing
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) throws NoRecordFoundException {
        bookService.deleteBook(id);
        return new ResponseEntity("Book deleted", HttpStatus.OK);
    }

    // Retrieve all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity(books, HttpStatus.OK);
    }

    // Search books based on title, author, or genre
    @GetMapping("/search")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.searchBooks(title, author, genre, pageable);
        return ResponseEntity.ok(books);
    }
}

