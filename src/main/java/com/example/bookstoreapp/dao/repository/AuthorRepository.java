package com.example.bookstoreapp.dao.repository;

import com.example.bookstoreapp.dao.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
