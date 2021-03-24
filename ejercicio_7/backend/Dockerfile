# Use AdoptOpenJDK image with JRE 14 based on alpine
FROM adoptopenjdk/openjdk14:jre-14.0.2_12-alpine


# Create a specific user for running our app
RUN addgroup -S appuser
RUN adduser -S appuser -G appuser

# Arguments needed for building this image
## JAR_FILE. Location of JAR file that will be run in this container
ARG JAR_FILE
RUN echo "JAR FILE: $JAR_FILE"

## Labels for this docker image
LABEL maintainer="ezequiel.aceto@gmail.com"
LABEL jar-file=${JAR_FILE}

# Copy JAR_FILE to applications directory
# Copy JAR_FILE to applications directory
COPY --chown=appuser:appuser ${JAR_FILE} /applications/app.jar

# Declare port 8080 as exposed in the container's network
EXPOSE 8080/tcp

# From now on, all commands as run as *appuser*
USER appuser

# Add a health check to verify if the service is running
HEALTHCHECK --start-period=10s --interval=60s --timeout=5s --retries=3 CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the app
CMD ["java","-jar","/applications/app.jar"]
