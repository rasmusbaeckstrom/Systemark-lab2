package org.example.entities;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ProductRecord(
        @Min(value = 1, message = "Id should not be less than 1")
        int id,
        @NotEmpty(message = "Empty names not allowed")
        String name,
        @NotNull(message = "Category should not be null")
        Category category,
        @Min(1) @Max(10)
        int rating,
        @PastOrPresent
        LocalDateTime createdDate,
        LocalDateTime modifiedDate) {
}