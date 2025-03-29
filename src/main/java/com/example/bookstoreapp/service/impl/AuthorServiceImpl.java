package com.example.bookstoreapp.service.impl;

import com.example.bookstoreapp.dao.entity.Author;
import com.example.bookstoreapp.dao.entity.Book;
import com.example.bookstoreapp.dao.repository.AuthorRepository;
import com.example.bookstoreapp.dto.request.AuthorRequest;
import com.example.bookstoreapp.dto.response.AuthorResponse;
import com.example.bookstoreapp.exception.ResourceNotFoundException;
import com.example.bookstoreapp.mapper.AuthorMapper;
import com.example.bookstoreapp.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorServiceImpl implements AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    @Override
    public AuthorResponse getAuthor(String id) {
        log.info("Operation of getting author with ID {} started", id);
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Author.class));
        AuthorResponse response = authorMapper.toDto(author);
        log.info("Author with ID {} returned to user", id);
        return response;
    }

    @Override
    public Page<AuthorResponse> getAllAuthors(Pageable pageable) {
        log.info("Operation of getting authors started");
        Page<Author> authors = authorRepository.findAll(pageable);
        Page<AuthorResponse> response = authors.map(authorMapper::toDto);
        log.info("Authors returned to user");
        return response;
    }

    @Override
    @Transactional
    public AuthorResponse addAuthor(AuthorRequest authorRequest) {
        log.info("Operation of adding author started");
        Author author = authorMapper.toEntity(authorRequest);
        authorRepository.save(author);
        log.info("New author successfully added");
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional
    public AuthorResponse updateAuthor(String id, AuthorRequest authorRequest) {
        log.info("Operation of updating author with ID {} started", id);
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Author.class));
        authorMapper.mapForUpdate(author, authorRequest);
        author.setId(id);
        Author updatedAuthor = authorRepository.save(author);
        log.info("Author with ID {} successfully updated", id);
        return authorMapper.toDto(updatedAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthor(String id) {
        log.info("Operation of deleting author with ID {} started", id);
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Author.class));
        //MongoDB doesn't support cascade delete-so when we delete author it doesn't mean that all associated books also be deleted(Remember)
        authorRepository.delete(author);
        log.info("Author with ID {} successfully deleted", id);
    }
}
