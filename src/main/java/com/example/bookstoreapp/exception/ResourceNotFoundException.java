package com.example.bookstoreapp.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> entity) {
        super(entity.getSimpleName().toUpperCase() + "_NOT_FOUND");
    }
}
