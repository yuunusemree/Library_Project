package com.mylibraryproject.repository;

import com.mylibraryproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.bookName = ?1")
    Book findByBookName(String bookName);

    Optional<Book> findById(Long id);

    @Override
    void delete(Book book);
}
