# Stage 1: build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine

ENV PORT=8081
ENV SPRING_PROFILE="dev-docker"
ARG VERSION="0.1.0"
ARG NAME="account-service"

# Add Metadata
LABEL maintainer="Trevor Busk <trevor@tbusk.com>"

# Set up group and user
RUN addgroup -S application && \
    adduser -S -G application user

# Copy files over while updating permissions and group
COPY --chown=user:application --from=build /app/build/libs/${NAME}-${VERSION}.jar app.jar

# Get Curl for healthchecks
RUN apk add --no-cache curl

# Switch from Root to User
USER user

EXPOSE ${PORT}

ENTRYPOINT exec java -Dfile.encoding=UTF-8 -Dspring.profiles.active=${SPRING_PROFILE} -jar /app.jar