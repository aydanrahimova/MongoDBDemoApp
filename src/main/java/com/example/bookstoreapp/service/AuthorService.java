package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.request.AuthorRequest;
import com.example.bookstoreapp.dto.response.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponse getAuthor(String id);

    Page<AuthorResponse> getAllAuthors(Pageable pageable);

    AuthorResponse addAuthor(AuthorRequest authorRequest);

    AuthorResponse updateAuthor(String id, AuthorRequest authorRequest);

    void deleteAuthor(String id);
}
