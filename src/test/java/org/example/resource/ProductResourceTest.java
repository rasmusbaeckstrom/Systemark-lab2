package org.example.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ProductResourceTest {

    Dispatcher dispatcher;
    WarehouseService warehouseService;

    @BeforeEach
    public void setUp() {
        warehouseService = Mockito.mock(WarehouseService.class);
        ProductResource productResource = new ProductResource(warehouseService);
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(productResource);
    }

    @Test
    void whenPostingJsonRepresentingProductThenShouldGet201Created() throws URISyntaxException, JsonProcessingException {
        // Mock the service method
        when(warehouseService.getProductById(anyInt())).thenReturn(Optional.of(
                new ProductRecord(66, "asd", Category.BOOKS, 10, new Date(), new Date())
        ));

        // Create a mock request
        MockHttpRequest request = MockHttpRequest.post("/products");
        String json = new ObjectMapper().writeValueAsString(new ProductRecord(66, "asd", Category.BOOKS, 10, new Date(), new Date()));
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);

        // Create a mock response
        MockHttpResponse response = new MockHttpResponse();

        // Invoke the request
        dispatcher.invoke(request, response);

        // Assert the response status code
        assertEquals(201, response.getStatus());
    }
}