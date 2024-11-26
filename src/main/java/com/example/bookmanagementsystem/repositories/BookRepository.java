package com.example.bookmanagementsystem.repositories;

import com.example.bookmanagementsystem.entities.Book;
import com.example.bookmanagementsystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainingIgnoreCase(String title);

   List<Book> findByCategoryCategoryId(Integer id);

}
