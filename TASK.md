# Booking System

This is a Java application for a booking system, implemented as a monolithic REST API service using Spring Boot, Spring Web, and Spring Data.

## Application Description

Users can add new booking subjects called **Units** with the following properties:

- **Number of rooms**
- **Type of accommodation**: `HOME`, `FLAT`, `APARTMENTS`
- **Unit’s floor**
- **Availability**: determined by bookings for a range of dates
- **Cost**: specified by the user on creation, plus a 15% booking system markup
- **General description**: specified by the user

### Features

- Add new Units
- Query Units by all specified criteria, including date range and cost
- Sortable list with custom pagination (page, number of objects per page)
- Book a Unit (makes it unavailable for others)
- Cancel a booking for a selected Unit
- Emulate payment for a booking (without payment, booking is auto-cancelled after 15 minutes)

### Caching

- Main statistics (number of Units available for booking) are stored in cache
- Caching system can be a thread-safe Map or an external system (Redis, Memcached, Hazelcast, etc.)
- Cache updates on each Unit status change and can recover after a system crash
- Endpoint to obtain the number of Units available for booking

## Requirements

- Java 21 or newer
- Preferably use Gradle for building the project
- Do **not** use Spring Security (not necessary)
- Use PostgreSQL as the database
- Manage DB schema with Liquibase (any format: XML, SQL, YAML, JSON)
- Data management with Spring Data JPA or Spring Data JDBC
- Application can be started natively (without containers)
- PostgreSQL and other services should run in Docker containers (described with `docker-compose`)
- Models, controllers, and services should be covered by unit and functional tests (preferably JUnit 5 and Spring Test Framework)
- API documentation (preferably Swagger/OpenAPI Specification, dynamic or static)
- DB structure must include at least tables for Units, their properties, Users, payment, and events
- Input and output model validation can be skipped
- Add 10 Units with properties and creation events in the Liquibase ChangeLog
- Add 90 Units with random parameters and costs on application start

## How to Submit

Archive the project and send it to the HR manager.

