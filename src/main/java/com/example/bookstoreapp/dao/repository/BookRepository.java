package com.example.bookstoreapp.dao.repository;

import com.example.bookstoreapp.dao.entity.Author;
import com.example.bookstoreapp.dao.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
    Page<Book> findAllByAuthor(Author author, Pageable pageable);
}
