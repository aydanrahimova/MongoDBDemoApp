package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.request.AuthorRequest;
import com.example.bookstoreapp.dto.response.AuthorResponse;
import com.example.bookstoreapp.service.AuthorService;
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
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {
    AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorResponse getAuthor(@PathVariable String id) {
        return authorService.getAuthor(id);
    }

    @GetMapping
    public Page<AuthorResponse> getAuthors(@PageableDefault Pageable pageable) {
        return authorService.getAllAuthors(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorResponse addAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        return authorService.addAuthor(authorRequest);
    }

    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(@PathVariable String id, @Valid @RequestBody AuthorRequest authorRequest) {
        return authorService.updateAuthor(id, authorRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
    }
}
