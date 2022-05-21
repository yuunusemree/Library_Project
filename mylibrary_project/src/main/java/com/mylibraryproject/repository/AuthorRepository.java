package com.mylibraryproject.repository;

import com.mylibraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.authorName = ?1")
    Optional<Author> findByName(String authorName);

    Optional<Author> findById(Long id);

    @Override
    void delete(Author author);
}
