# Stage 1: Build the application using Maven and Java 21
FROM maven:3.8.7-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and run the tests
RUN mvn clean package

# Stage 2: Use Wildfly image from Quay.io as a base
FROM quay.io/wildfly/wildfly:latest

# Set the working directory inside the container
WORKDIR /opt/jboss/wildfly/standalone/deployments/

# Copy the built WAR file from the build stage to Wildfly's deployments folder
COPY --from=build /app/target/Systemark-lab2-1.0-SNAPSHOT.war ./

# Expose the default Wildfly port
EXPOSE 8080

# Start Wildfly server
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
