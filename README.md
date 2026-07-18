# Problem Tracker

This is a sample competitive programming and interview problem utility microservices monorepo allowing users to keep
track of problems they have solved, what platform, what language, how quick, when, how often, and to get notifications
of what they should work on, their activity (daily, weekly, monthly, yearly, etc.), and anything else that comes to mind
when building it.

## Setup

**Config Server**:

This server is responsible for managing the configuration for microservices and servers in the application.

- Spring Cloud Config Server → [config-server](config-server)

**API Gateway**:

This is responsible for authentication via JWT and routing requests to their intended microservices.

- Spring Cloud Gateway → [problem-gateway](problem-gateway)

**Discovery Service**:

This is responsible for registering and discovering microservices in the application.

- Spring Cloud Eureka Server → [service-discovery](service-discovery)

**Microservices**:

This microservice is responsible for managing problems and their associated data.

- Java & Spring Boot → [problem-service](problem-service)

This microservice is responsible for managing user accounts and their associated data.

- Java & Spring Boot → [account-service](account-service)

This microservice is responsible for generating reports and analytics based on user data related to problems and user
activity.

- Report Service [Later]

This microservice is responsible for generating recommendations based on user activity and goals.

- Recommendation Service [Later]

**Databases**:

This database is responsible for storing data related to problems, users, and user activity.

- Oracle DB

## Prerequisites

- Java 21
- Docker
- Docker Compose

## Getting Started

To get started:

1. Clone the repository.
2. Navigate to [development](development)
3. Run `docker compose up -d database` to bootstrap the database
4. Navigate to [development/oracle_db](development/oracle_db), read the README, follow any instructions, navigate
   to [development/oracle_db/schema](development/oracle_db/schema) and run the SQL scripts to set up the database
   schema, paying attention to ordering.

### Development with Docker

To get started:

1. Run `docker compose up -d` to get the remaining services up

### Development

To get started:

1. Head to [config server](config-server), start it, and review the configuration files and adjustdock as needed in
   the [resources/config](config-server/src/main/resources/config) directory.
2. Head to [service-discovery](service-discovery) and start it.
3. Head to [problem-gateway](problem-gateway) and start it. Send your requests through this with the service name as a
   prefix.
4. Head to each of the microservices ([problem-service](problem-service), [account-service](account-service)) and start
   them.

## Agenda

- [ ] Add advice to possible thrown exceptions in each service
- [ ] Move authentication from gateway to a new authentication microservice
- [ ] Finish implementing and setting up the authentication microservice
- [ ] Get user problem fetching set up
- [ ] Test existing functionality more
- [ ] Expand problem service functionality

## Why Build This?

Currently, I keep track of what problem's I've solved, when, and how many times. I'd like to expand this, and make it
easier for me to track so I can keep up to date and not miss out on any categories or forget how to solve a particular
problem.

Additionally, this is an exploratory project for me to fill in some gaps in my experience, such as:

- Microservices
- Oracle DB
- UI Development, specifically try out a few different kinds, like TUI or Angular
- Kubernetes
- Cloud Providers like Azure or areas in cloud providers that I lack a lot of experience, such as GCP and AWS with EKS
- Jenkins build system
- Testing, specifically with UI, full system, load, stress, etc.

## License

This project is licensed under the [MIT License](LICENSE).