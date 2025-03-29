package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.dao.entity.Author;
import com.example.bookstoreapp.dto.request.AuthorRequest;
import com.example.bookstoreapp.dto.response.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse toDto(Author author);

    void mapForUpdate(@MappingTarget Author author, AuthorRequest authorRequest);

    Author toEntity(AuthorRequest authorRequest);
}
