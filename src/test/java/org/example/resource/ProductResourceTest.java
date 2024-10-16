package org.example.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.exceptionmapper.ProductNotFoundExceptionMapper;
import org.example.service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


class ProductResourceTest {

    Dispatcher dispatcher;
    WarehouseService warehouseService;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        warehouseService = Mockito.mock(WarehouseService.class);
        ProductResource productResource = new ProductResource(warehouseService);
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(productResource);
        objectMapper = new ObjectMapper();
        dispatcher.getProviderFactory().register(ProductNotFoundExceptionMapper.class);
    }

    @Test
    void whenPostingJsonRepresentingProductThenShouldGet201Created() throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {
        ProductRecord expectedProduct = new ProductRecord(66, "asd", Category.BOOKS, 10, new Date(), new Date());
        when(warehouseService.getProductById(anyInt())).thenReturn(Optional.of(expectedProduct));

        MockHttpRequest request = MockHttpRequest.post("/products");
        String json = objectMapper.writeValueAsString(expectedProduct);
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(201, response.getStatus());
        ProductRecord actualProduct = objectMapper.readValue(response.getContentAsString(), ProductRecord.class);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void whenGetAllProductsThenShouldGet200Ok() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void whenGetProductByIdThenShouldGet200Ok() throws URISyntaxException {
        when(warehouseService.getProductById(anyInt())).thenReturn(Optional.of(
                new ProductRecord(66, "asd", Category.BOOKS, 10, new Date(), new Date())
        ));

        MockHttpRequest request = MockHttpRequest.get("/products/66");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void whenGetProductsByCategoryThenShouldGet200Ok() throws URISyntaxException {
        when(warehouseService.getAllProductsByCategorySortedByProductName(Category.BOOKS)).thenReturn(
                List.of(new ProductRecord(66, "asd", Category.BOOKS, 10, new Date(), new Date()))
        );

        MockHttpRequest request = MockHttpRequest.get("/products/category/BOOKS");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void whenGetProductByIdThatDoesNotExistThenShouldGet404NotFound() throws URISyntaxException {
        when(warehouseService.getProductById(anyInt())).thenReturn(Optional.empty());

        MockHttpRequest request = MockHttpRequest.get("/products/999");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(404, response.getStatus());
    }

    @Test
    void whenGetProductsByNonExistentCategoryThenShouldGet200OkWithEmptyList() throws URISyntaxException, UnsupportedEncodingException {
        when(warehouseService.getAllProductsByCategorySortedByProductName(Category.TOYS)).thenReturn(List.of());

        MockHttpRequest request = MockHttpRequest.get("/products/category/TOYS");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }
}