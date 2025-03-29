package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.dao.entity.Book;
import com.example.bookstoreapp.dto.request.BookRequest;
import com.example.bookstoreapp.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequest bookRequest);

    BookResponse toDto(Book book);

    void mapForUpdate(@MappingTarget Book book, BookRequest bookRequest);
}
