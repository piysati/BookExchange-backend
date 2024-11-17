package com.bits_wilp.bookexchangeplatform.service;

import com.bits_wilp.bookexchangeplatform.dto.BookDTO;
import com.bits_wilp.bookexchangeplatform.entity.Book;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    void addBook(BookDTO bookDTO); // Adds a new book listing
    BookDTO updateBook(Long bookId, BookDTO bookDTO); // Updates an existing book
    void deleteBook(Long bookId) throws NoRecordFoundException; // Deletes a book listing by ID
    Book getBook(String name);
    List<Book> getAllBooks(); // Retrieves all books
    Page<Book> searchBooks(String title, String author, String genre, Pageable pageable); // Searches for books
}

