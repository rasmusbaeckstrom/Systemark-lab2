package org.example.entities;

import jakarta.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

public class Product {
    private final int id;
    private String name;
    private Category category;
    private int rating;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private final Date createdDate;
    private Date modifiedDate;

    public Product(int id, String name, Category category, int rating, Date createdDate) {
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
        this.modifiedDate = new Date();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.modifiedDate = new Date();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        this.modifiedDate = new Date();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }
}