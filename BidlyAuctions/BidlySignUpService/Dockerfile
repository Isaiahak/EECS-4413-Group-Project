# Start your image with a node base image
FROM openjdk:23-jdk-slim

# The /app directory should act as the main application directory
WORKDIR /app/bidly-sign-up-service

# Copy the WAR file from your local machine to the container's working directory
COPY target/BidlySignUpService-0.0.1-SNAPSHOT.war /app/bidly-sign-up-service/BidlySignUpService-0.0.1-SNAPSHOT.war

# Step 4: Expose the port your application runs on
EXPOSE 8081

# Step 5: Define the command to run your application
ENTRYPOINT ["java", "-jar", "/app/bidly-sign-up-service/BidlySignUpService-0.0.1-SNAPSHOT.war"]
