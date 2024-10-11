package org.example.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ProductNotFoundExceptionMapper implements ExceptionMapper<ProductNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(ProductNotFoundException exception) {
        logger.warn("Product not found: {}", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Product not found: " + exception.getMessage())
                .build();
    }
}
