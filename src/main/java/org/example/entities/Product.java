package org.example.entities;

import java.time.OffsetDateTime;
import jakarta.json.bind.annotation.JsonbDateFormat;

public class Product {
    private final int id;
    private String name;
    private Category category;
    private int rating;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    private final OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;

    public Product(int id, String name, Category category, int rating, OffsetDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.modifiedDate = OffsetDateTime.now();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.modifiedDate = OffsetDateTime.now();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        this.modifiedDate = OffsetDateTime.now();
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}