package com.bits_wilp.bookexchangeplatform.repository;

import com.bits_wilp.bookexchangeplatform.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    // Example of a query method for search functionality
    Book findByTitle(String name);

    @Query("SELECT b FROM Book b " +
            "WHERE (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:author IS NULL OR b.author LIKE %:author%) " +
            "AND (:genre IS NULL OR b.genre LIKE %:genre%)")
    Page<Book> findByTitleContainingOrAuthorContainingOrGenreContaining(
            @Param("title") String title,
            @Param("author") String author,
            @Param("genre") String genre,
            Pageable pageable);

    List<Book> findByAvailabilityTrue(); // Get only available books
}
