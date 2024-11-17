package com.bits_wilp.bookexchangeplatform.service;

import com.bits_wilp.bookexchangeplatform.dto.BookDTO;
import com.bits_wilp.bookexchangeplatform.entity.Book;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;
import com.bits_wilp.bookexchangeplatform.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public void addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setAvailability(true);

        this.bookRepo.save(book);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book updatedBook = this.bookRepo.getReferenceById(bookId);
        updatedBook.setTitle(bookDTO.getTitle());
        updatedBook.setAuthor(bookDTO.getAuthor());
        updatedBook.setGenre(bookDTO.getGenre());
        updatedBook.setAvailability(true);
        updatedBook.setId(bookDTO.getId());

        this.bookRepo.save(updatedBook);
        return null;
    }

    @Override
    public void deleteBook(Long bookId) throws NoRecordFoundException {
        Book bookToDelete = this.bookRepo.getReferenceById(bookId);
        if (bookToDelete == null)
            throw new NoRecordFoundException("No such record found with this book id.");

        this.bookRepo.delete(bookToDelete);
    }

    @Override
    public Book getBook(String name) {
        Book getBookWithID = this.bookRepo.findByTitle(name);
        return getBookWithID;
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }

    @Override
    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {

        // Convert empty strings to null to match the query condition
        title = (title == null || title.isEmpty()) ? null : title;
        author = (author == null || author.isEmpty()) ? null : author;
        genre = (genre == null || genre.isEmpty()) ? null : genre;

        return this.bookRepo.findByTitleContainingOrAuthorContainingOrGenreContaining(title, author, genre, pageable);
    }
}
