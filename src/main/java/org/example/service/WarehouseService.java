package org.example.service;

import org.example.entities.Category;
import org.example.entities.ProductRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WarehouseService {
    private static final WarehouseService instance = new WarehouseService();
    private final Warehouse warehouse = new Warehouse();
    private final Lock lock = new ReentrantLock();

    private WarehouseService() {}

    public static WarehouseService getInstance() {
        return instance;
    }

    public void addProduct(int id, String name, Category category, int rating, LocalDateTime createdDate) {
        lock.lock();
        try {
            warehouse.addProduct(id, name, category, rating, createdDate);
            Optional<ProductRecord> productRecord = warehouse.getProductById(id);
            if (productRecord.isPresent()) {
                ProductRecord record = productRecord.get();
            }
        } finally {
            lock.unlock();
        }
    }

    public List<ProductRecord> getAllProducts() {
        lock.lock();
        try {
            List<ProductRecord> products = warehouse.getAllProducts();
            return products;
        } finally {
            lock.unlock();
        }
    }

    public Optional<ProductRecord> getProductById(int id) {
        lock.lock();
        try {
            return warehouse.getProductById(id);
        } finally {
            lock.unlock();
        }
    }

    public boolean updateProduct(int id, String newName, Category newCategory, int newRating) {
        lock.lock();
        try {
            return warehouse.updateProduct(id, newName, newCategory, newRating);
        } finally {
            lock.unlock();
        }
    }

    public List<ProductRecord> getAllProductsByCategorySortedByProductName(Category category) {
        lock.lock();
        try {
            return warehouse.getAllProductsByCategorySortedByProductName(category);
        } finally {
            lock.unlock();
        }
    }

    public List<ProductRecord> getAllProductsCreatedAfterASpecificDate(LocalDateTime date) {
        lock.lock();
        try {
            return warehouse.getAllProductsCreatedAfterASpecificDate(date);
        } finally {
            lock.unlock();
        }
    }

    public List<ProductRecord> getAllProductsThatHasBeenModifiedSinceCreation() {
        lock.lock();
        try {
            return warehouse.getAllProductsThatHasBeenModifiedSinceCreation();
        } finally {
            lock.unlock();
        }
    }

    public Set<Category> getAllCategoriesThatHasAtLeastOneProduct() {
        lock.lock();
        try {
            return warehouse.getAllCategoriesThatHasAtLeastOneProduct();
        } finally {
            lock.unlock();
        }
    }

    public long getNumberOfProductsInCategory(Category category) {
        lock.lock();
        try {
            return warehouse.getNumberOfProductsInCategory(category);
        } finally {
            lock.unlock();
        }
    }

    public Map<Character, Long> getNumberOfProductsStartingWithEachLetter() {
        lock.lock();
        try {
            return warehouse.getNumberOfProductsStartingWithEachLetter();
        } finally {
            lock.unlock();
        }
    }

    public List<ProductRecord> getAllProductsWithMaxRatingCreatedThisMonthSortedByDate() {
        lock.lock();
        try {
            return warehouse.getAllProductsWithMaxRatingCreatedThisMonthSortedByDate();
        } finally {
            lock.unlock();
        }
    }

    public void validateProductId(int id) {
        lock.lock();
        try {
            warehouse.validateProductId(id);
        } finally {
            lock.unlock();
        }
    }

    public void checkIfProductIdExists(int id) {
        lock.lock();
        try {
            warehouse.checkIfProductIdExists(id);
        } finally {
            lock.unlock();
        }
    }
}
