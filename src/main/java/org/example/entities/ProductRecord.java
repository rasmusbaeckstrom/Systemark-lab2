package org.example.entities;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ProductRecord(
        @Min(value = 1, message = "Product ID must be at least 1.")
        int id,
        @NotEmpty(message = "Product name cannot be empty.")
        String name,
        @NotNull(message = "Product category cannot be null.")
        Category category,
        @Min(value = 1, message = "Product rating must be at least 1.")
        @Max(value = 10, message = "Product rating must be at most 10.")
        int rating,
        @PastOrPresent(message = "Product creation date cannot be in the future.")
        LocalDateTime createdDate,
        LocalDateTime modifiedDate) {
}