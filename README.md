EventFlow SaaS 🎫
High-Performance Multi-Tenant Ticketing Platform

EventFlow is a robust SaaS application designed for event organizers to manage halls, events, and ticket sales. Built with Java 21 and Spring Boot, it features a Schema-based Multi-tenancy architecture and handles high-concurrency scenarios (e.g., ticket "flash sales") using advanced locking mechanisms.

🚀 Key Features
🏢 Schema-Based Multi-Tenancy:

Implements Database-per-Tenant architecture using Hibernate's MultiTenantConnectionProvider and CurrentTenantIdentifierResolver.

Complete data isolation: Each organizer (Tenant) operates in their own PostgreSQL schema, while shared data resides in the public schema.

Dynamic schema switching based on JWT claims or request context.

🔒 Concurrency Control (Double-Booking Prevention):

Solves the "Race Condition" problem during high-load ticket sales.

Uses PESSIMISTIC_WRITE locking on database rows to ensure strict inventory consistency.

Prevents overselling even when thousands of users attempt to buy the last seat simultaneously.

⏳ Smart Reservation System:

Inventory Hold: Tickets are temporarily reserved (locked) for 10 minutes during the checkout process.

Automated Cleanup: A scheduled background job (Spring Scheduler) automatically releases expired bookings back to the pool (AVAILABLE status).

⚡ Async Processing:

Decoupled architecture using @Async execution.

Non-blocking email notifications for booking confirmations to ensure low latency for the user API.

🛠 Modern Infrastructure:

Flyway: Automated database migrations for both public and dynamic tenant schemas.

Testcontainers: Integration testing with real PostgreSQL instances in Docker containers.

Redis: Used for JWT blocklisting (Logout mechanism).

🛠 Tech Stack
Language: Java 21

Framework: Spring Boot 3.x

Database: PostgreSQL (Schema Multi-tenancy)

ORM: Hibernate / Spring Data JPA

Caching/Security: Redis, Spring Security, JWT (JJWT)

Migration: Flyway

Testing: JUnit 5, Mockito, Testcontainers

Tools: Maven, Docker, Lombok, MapStruct

🏗 Architecture Highlights
Multi-Tenancy Implementation
The application identifies the tenant from the JWT token. A custom TenantInterceptor sets the context, and Hibernate routes the database connection to the specific schema (e.g., schema_tenant_1) instead of the default public schema.

The "Hard" Part: Buying a Ticket
Lock: Transaction starts -> Select row FOR UPDATE (Pessimistic Lock).

Verify: Check if seat status is AVAILABLE.

Reserve: Change status to RESERVED and create a PENDING booking.

Commit: Transaction ends, lock releases.

Notify: Email service triggers asynchronously.

⚙️ Getting Started
Prerequisites
Java 21 SDK

Docker (for PostgreSQL and Redis)

Maven

Installation
Clone the repository:

Bash
git clone https://github.com/BeCooN0/EventFlowSaaS.git cd EventFlowSaaS
cd EventFlowSaaS
Set up Database & Redis: You need a running PostgreSQL instance on port 5432 and Redis on 6379. Update src/main/resources/application.properties with your credentials if needed.

Build and Run:

Bash
./mvnw spring-boot:run
Migrations: Flyway will automatically create the public schema tables. When a new Tenant is registered via API, the application creates a separate schema for them at runtime.

🧪 Testing
The project uses Testcontainers to ensure tests run against a real database environment.

Bash
./mvnw test
📑 API Endpoints (Brief)
POST /api/auth/register - Register a new organizer (Tenant)

POST /api/auth/login - Login and receive JWT

GET /api/events - List events (Tenant specific)

POST /api/booking/add - Reserve a ticket (Concurrency protected)

POST /api/seats/generate/{eventId} - Admin: Generate seat map
