package org.example.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/products")
public class ProductResource {
    private final WarehouseService warehouseService = new WarehouseService();
    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductRecord productRecord) {
        logger.info("Adding product: {}", productRecord);
        try {
            warehouseService.addProduct(productRecord.id(), productRecord.name(), productRecord.category(), productRecord.rating(), productRecord.createdDate());
            logger.info("Product added successfully {}", productRecord);
            return Response.status(Response.Status.CREATED).entity("Product added successfully").build();
        } catch (IllegalArgumentException e) {
            logger.error("Error adding product: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<ProductRecord> products = warehouseService.getAllProducts();
        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        logger.info("Received request to get product by ID: {}", id);
        var product = warehouseService.getProductById(id);
        return product.map(p -> {
            logger.info("Product found: {}", p);
            return Response.ok(p).build();
        }).orElseGet(() -> {
            logger.warn("Product not found with ID: {}", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        });
    }

    @GET
    @Path("/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") Category category) {
        logger.info("Received request to get products by category: {}", category);
        List<ProductRecord> products = warehouseService.getAllProductsByCategorySortedByProductName(category);
        logger.info("Returning {} products for category: {}", products.size(), category);
        return Response.ok(products).build();
    }
}