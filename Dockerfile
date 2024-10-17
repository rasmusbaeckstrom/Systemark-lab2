# Use Wildfly image from Quay.io as a base
FROM quay.io/wildfly/wildfly:latest

# Set the working directory inside the container
WORKDIR /opt/jboss/wildfly/standalone/deployments/

# Copy the built WAR file to the deployments directory
COPY target/Systemark-lab2-1.0-SNAPSHOT.war .

# Expose the default Wildfly port
EXPOSE 8080

# Start Wildfly server
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]