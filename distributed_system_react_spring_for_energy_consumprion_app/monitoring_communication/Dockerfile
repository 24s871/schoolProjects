FROM openjdk:21
WORKDIR /app

# Copy the jar file from the target directory
COPY target/monitoring_communication-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8081
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]