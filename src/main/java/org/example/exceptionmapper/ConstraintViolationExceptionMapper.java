package org.example.exceptionmapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        logger.warn("Validation error: {}", exception.getMessage());

        String validationErrors = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Validation error(s): " + validationErrors)
                .build();
    }
}

