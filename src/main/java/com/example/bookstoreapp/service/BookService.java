package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.request.BookRequest;
import com.example.bookstoreapp.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse getBook(String id);

    Page<BookResponse> getBooks(Pageable pageable);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(String id, BookRequest bookRequest);

    void deleteBook(String id);

    Page<BookResponse> getBooksByAuthor(Pageable pageable, String authorId);
}
