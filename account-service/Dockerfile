# Stage 1: build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine

ENV PORT=8080
ENV SPRING_PROFILE="dev-docker"
ARG VERSION="0.1.0"
ARG NAME="account-service"

# Add Metadata
LABEL maintainer="Trevor Busk <trevor@tbusk.com>"

# Set up group and user
RUN addgroup -S account-service && \
    adduser -S -G account-service service-user

# Copy files over while updating permissions and group
COPY --chown=service-user:account-service --from=build /app/build/libs/${NAME}-${VERSION}.jar app.jar

# Switch from Root to User
USER service-user

EXPOSE ${PORT}

ENTRYPOINT exec java -Dfile.encoding=UTF-8 -Dspring.profiles.active=${SPRING_PROFILE} -jar /app.jar