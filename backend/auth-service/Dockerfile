# Stage 1: Build the application
FROM gradle:8.8.0-jdk17 AS build

WORKDIR /build

# Copy Gradle wrapper and configuration
COPY --chmod=0755 gradlew gradlew
COPY ./gradle ./gradle
COPY build.gradle settings.gradle ./

# Copy src
COPY ./src src/

# Run gradle build
RUN gradle bootJar -i -x test

# Stage 2: Extract the built artifact
FROM build AS extract

WORKDIR /build

RUN java -Djarmode=layertools -jar build/libs/*.jar extract --destination /extracted

# Stage 3: Create the final image
FROM eclipse-temurin:17-jre-jammy AS final

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/go/dockerfile-user-best-practices/
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "extract" stage.
COPY --from=extract /extracted/dependencies/ ./
COPY --from=extract /extracted/spring-boot-loader/ ./
COPY --from=extract /extracted/snapshot-dependencies/ ./
COPY --from=extract /extracted/application/ ./

EXPOSE 8080

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
