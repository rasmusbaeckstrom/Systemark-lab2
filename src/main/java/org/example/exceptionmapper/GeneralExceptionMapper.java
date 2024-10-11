package org.example.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        logger.error("Unexpected error occurred: ", exception);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An unexpected error occurred. Please try again later.")
                .build();
    }
}
