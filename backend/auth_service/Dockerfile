FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Entry point use the class in build/resolvedMainClassName
ENTRYPOINT ["java","-cp","app:app/lib/*","com.petinder.auth_service.AuthServiceApplication"]