# Start your image with a node base image
FROM openjdk:23-jdk-slim

# The /app directory should act as the main application directory
WORKDIR /app/bidly-catalogue

# Copy the WAR file from your local machine to the container's working directory
COPY target/BidlyCatalogue-0.0.1-SNAPSHOT.war /app/bidly-catalogue/BidlyCatalogue-0.0.1-SNAPSHOT.war

# Step 4: Expose the port your application runs on
EXPOSE 8084

# Step 5: Define the command to run your application
ENTRYPOINT ["java", "-jar", "/app/bidly-catalogue/BidlyCatalogue-0.0.1-SNAPSHOT.war"]
