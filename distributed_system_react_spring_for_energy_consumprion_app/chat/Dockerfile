FROM openjdk:21
WORKDIR /app

# Copy the jar file from the target directory
COPY target/chat-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8084

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]