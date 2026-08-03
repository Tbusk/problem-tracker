# Stage 1: build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
ENV PORT=8082
ENV SPRING_PROFILE="dev-docker"
ARG VERSION="0.1.0"
ARG NAME="authentication-service"
LABEL maintainer="Trevor Busk <trevor@tbusk.com>"
RUN addgroup -S application && \
    adduser -S -G application user
COPY --chown=user:application --from=build /app/build/libs/${NAME}-${VERSION}.jar app.jar
RUN apk add --no-cache curl
USER user
EXPOSE ${PORT}
ENTRYPOINT exec java -Dfile.encoding=UTF-8 -Dspring.profiles.active=${SPRING_PROFILE} -jar /app.jar
