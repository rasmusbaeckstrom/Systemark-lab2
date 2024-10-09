package org.example.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:63342");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
    }
}