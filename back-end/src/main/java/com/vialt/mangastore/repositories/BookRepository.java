package com.vialt.mangastore.repositories;

import com.vialt.mangastore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContaining(String name);
    List<Book> findByCategoryId(Long id);
    List<Book> findBookById(Long id);

}
