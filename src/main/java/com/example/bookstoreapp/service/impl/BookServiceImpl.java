package com.example.bookstoreapp.service.impl;

import com.example.bookstoreapp.dao.entity.Author;
import com.example.bookstoreapp.dao.entity.Book;
import com.example.bookstoreapp.dao.repository.AuthorRepository;
import com.example.bookstoreapp.dao.repository.BookRepository;
import com.example.bookstoreapp.dto.request.BookRequest;
import com.example.bookstoreapp.dto.response.BookResponse;
import com.example.bookstoreapp.exception.ResourceNotFoundException;
import com.example.bookstoreapp.mapper.BookMapper;
import com.example.bookstoreapp.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    AuthorRepository authorRepository;

    @Override
    public BookResponse getBook(String id) {
        log.info("Operation of getting book with ID {} started", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Book.class));
        BookResponse response = bookMapper.toDto(book);
        log.info("Book with ID {} returned to user", id);
        return response;
    }

    @Override
    public Page<BookResponse> getBooks(Pageable pageable) {
        log.info("Operation of getting books started");
        Page<Book> books = bookRepository.findAll(pageable);
        Page<BookResponse> response = books.map(bookMapper::toDto);
        log.info("Books returned to user");
        return response;
    }

    @Override
    @Transactional
    public BookResponse addBook(BookRequest bookRequest) {
        log.info("Operation of adding book started");
        Author author = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException(Author.class));
        Book book = bookMapper.toEntity(bookRequest);
        book.setAuthor(author);
        bookRepository.save(book);
        author.getBooks().add(book);
        authorRepository.save(author);
        log.info("New book successfully added");
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public BookResponse updateBook(String id, BookRequest bookRequest) {
        log.info("Operation of updating book with ID {} started", id);
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(Book.class));
        Author author = book.getAuthor();
        if (!author.getId().equals(bookRequest.getAuthorId())) {
            Author newAuthor = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(() ->
                    new ResourceNotFoundException(Author.class));
            author.getBooks().remove(book);
            authorRepository.save(author);

            newAuthor.getBooks().add(book);
            book.setAuthor(newAuthor);

            authorRepository.save(newAuthor);
        }
        bookMapper.mapForUpdate(book, bookRequest);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        log.info("Book with ID {} successfully updated", id);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        log.info("Operation of deleting book with ID {} started", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book with ID " + id + " not found"));
        Author author = book.getAuthor();
        author.getBooks().remove(book);
        authorRepository.save(author);
        bookRepository.delete(book);
        log.info("Book with ID {} successfully deleted", id);
    }

    @Override
    public Page<BookResponse> getBooksByAuthor(Pageable pageable, String authorId) {
        log.info("Operation of getting book of author with ID {} started", authorId);
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException(Author.class));
        Page<Book> books = bookRepository.findAllByAuthor(author, pageable);
        Page<BookResponse> bookResponses = books.map(bookMapper::toDto);
        log.info("{} books of author with ID {} returned", bookResponses.getTotalElements(), authorId);
        return bookResponses;
    }
}
