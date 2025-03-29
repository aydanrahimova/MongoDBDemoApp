package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.request.BookRequest;
import com.example.bookstoreapp.dto.response.BookResponse;
import com.example.bookstoreapp.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {

    BookService bookService;

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return bookService.getBook(id);
    }

    @GetMapping
    public Page<BookResponse> getBooks(@PageableDefault Pageable pageable) {
        return bookService.getBooks(pageable);
    }

    @GetMapping("/author/{authorId}")
    public Page<BookResponse> getBooksByAuthor(@PageableDefault Pageable pageable, @PathVariable String authorId) {
        return bookService.getBooksByAuthor(pageable, authorId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookResponse addBook(@Valid @RequestBody BookRequest bookRequest) {
        return bookService.addBook(bookRequest);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable String id, @Valid @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(id, bookRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }
}
