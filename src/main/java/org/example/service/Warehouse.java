package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;
import java.util.*;
import java.util.stream.Collectors;


public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Method to validate a product
    public void validateProduct(String name, int rating) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Product rating must be between 1 and 10.");
        }
    }

    // Method to validate product ID
    public void validateProductId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
    }

    // Method to check if product ID already exists
    public void checkIfProductIdExists(int id) {
        if (products.stream().anyMatch(p -> p.getId() == id)) {
            throw new IllegalArgumentException("Product ID already exists.");
        }
    }

    // Method to add a product
    public void addProduct(int id, String name, Category category, int rating, Date createdDate) {
        validateProductId(id);
        validateProduct(name, rating);
        checkIfProductIdExists(id);
        Product product = new Product(id, name, category, rating, createdDate);
        products.add(product);
    }

    // Method to get all products
    public List<ProductRecord> getAllProducts() {
        List<ProductRecord> productRecords = products.stream()
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(productRecords);
    }

    // Method to get a product by ID
    public Optional<ProductRecord> getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .findFirst();
    }

    // Method to update a product
    public boolean updateProduct(int id, String newName, Category newCategory, int newRating) {
        validateProductId(id);
        validateProduct(newName, newRating);
        Optional<Product> productOpt = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(newName);
            product.setCategory(newCategory);
            product.setRating(newRating);
            return true;
        } else {
            return false;
        }
    }

    // Method to get all products by category sorted by product name
    public List<ProductRecord> getAllProductsByCategorySortedByProductName(Category category) {
        List<ProductRecord> productRecords = products.stream()
                .filter(p -> p.getCategory().equals(category))
                .sorted(Comparator.comparing(Product::getName))
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(productRecords);
    }

    // Method to get all products created after a specific date
    public List<ProductRecord> getAllProductsCreatedAfterASpecificDate(Date date) {
        List<ProductRecord> productRecords = products.stream()
                .filter(p -> p.getCreatedDate().after(date))
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(productRecords);
    }

    // Method to get all products that have been modified since creation
    public List<ProductRecord> getAllProductsThatHasBeenModifiedSinceCreation() {
        List<ProductRecord> productRecords = products.stream()
                .filter(p -> p.getModifiedDate().after(p.getCreatedDate()))
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(productRecords);
    }

    // Method to get all Categories that has at least one product
    public Set<Category> getAllCategoriesThatHasAtLeastOneProduct() {
        Set<Category> categories = products.stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet());
        return Collections.unmodifiableSet(categories);
    }

    // Method to get how many products there are in given category
    public long getNumberOfProductsInCategory(Category category) {
        return products.stream()
                .filter(p -> p.getCategory().equals(category))
                .count();
    }

    // Method to get a Map that contains all the letters that product name start with as key and the number of products that start with that letter as value
    public Map<Character, Long> getNumberOfProductsStartingWithEachLetter() {
        Map<Character, Long> productsStartingWithEachLetter = products.stream()
                .collect(Collectors.groupingBy(p -> p.getName().charAt(0), Collectors.counting()));
        return Collections.unmodifiableMap(productsStartingWithEachLetter);
    }

    // Method to get all products with max rating, created this month and sorted by date with the latest first
    public List<ProductRecord> getAllProductsWithMaxRatingCreatedThisMonthSortedByDate() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = now.getTime();

        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        Date endOfMonth = now.getTime();

        List<ProductRecord> productRecords = products.stream()
                .filter(p -> p.getRating() == 10 && p.getCreatedDate().after(startOfMonth) && p.getCreatedDate().before(endOfMonth))
                .sorted(Comparator.comparing(Product::getCreatedDate).reversed())
                .map(p -> new ProductRecord(p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedDate(), p.getModifiedDate()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(productRecords);
    }
}