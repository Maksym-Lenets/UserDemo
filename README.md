# UserDemo API

This is a simple User resource API developed using the latest version of Spring Boot (Java version 17).
The API allows you to perform various user-related operations, including user creation, updates, deletions, and searching by date of birth  range. 
It also includes validation for user input and error handling for RESTful responses.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Features](#Features)
- [Dependencies](#Dependencies)
- [API Documentation](#api-documentation)
- [Database](#database)


## Getting Started

These instructions will help you get the project up and running on your local machine.

## Prerequisites

Before you begin, make sure you have the following software installed:

- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads) (optional)

## Installation

1. Clone the repository (if you haven't already): git clone https://github.com/Maksym-Lenets/UserDemo.git
2. Build and run the application using Maven: mvn spring-boot:run

The application should now be running locally at http://localhost:8080.

## Features
1. User Fields:
   - Email (required with email pattern validation)
   - First name (required)
   - Last name (required)
   - Date of birth (required, must be earlier than the current date)
   - Address (optional)
   - Phone number (optional)
2. Functionality:
   - Create a user: Register users who are more than 18 years old (the minimum age is configurable via the userdemo.validation.userMinAcceptableAge property).
   - Update one or more user fields. 
   - Update all user fields. 
   - Delete a user. 
   - Search for users by date of birth range (with validation that "From" is less than "To"). Returns a list of user objects.
3. Unit Testing: The code is covered by unit tests using Spring.
4. Error Handling: The API includes error handling for RESTful responses. 
5. JSON Responses: API responses are in JSON format. 
6. Database: The API supports H2 database as a runtime dependency but can be configured to use other databases as needed.

## Dependencies
* **[Spring Boot](https://spring.io/projects/spring-boot/)** - Latest version of Spring Boot.
* **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** - Data access and persistence.
* **[Spring Validation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation)** - Validation support.
* **[Springdoc OpenAPI](https://springdoc.org/)** - API documentation generation.
* **[H2 Database](https://www.h2database.com/html/main.html)** - In-memory database for development and testing.
* **[Lombok](https://projectlombok.org/)** - Simplify code with annotations.
* **[JUnit](https://junit.org/junit5/)** - Testing framework.
* **[Mockito](https://site.mockito.org/)** - Mocking framework for unit tests.

## API Documentation

The API documentation can be found at: http://localhost:8080/swagger-ui/index.html.
This documentation provides details about the available endpoints, request parameters, and expected responses.

## Database

The application uses H2 Database for local development. You can access the H2 console
at: http://localhost:8080/h2-console

* JDBC URL: jdbc:h2:mem:userdemo
* Username: sa
* Password: password