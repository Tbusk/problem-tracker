# Problem Gateway

This is a Spring Boot API gateway for all the microservices in the problem tracker application, which handles JWT
authentication and routing in addition to functionality like rate limiting and circuit breaking (adding later).

## Tech Stack

- Java Version 21
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security
- Spring Cloud Gateway Server
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Client
- Hibernate Validator
- Spring Boot Actuator
- Oracle DB
- Docker
- GitHub
- Git
- JWT Authentication
- Gradle

### Development-Related

- Spring Boot DevTools
- Spring Boot Configuration Processor
- OpenAPI/Swagger API Documentation (via Javadoc)

### Testing

- JUnit 5 [Planned]
- Mockito [Planned]

## Current Features

- JWT Authentication
- Routing through service discovery and client-side load balancing

## Potential Features

- Rate limiting
- Circuit breaking
- Retries
- Bulkhead pattern
- Fallback processing
- Logging