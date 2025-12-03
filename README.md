# StaySphere Backend

A Spring Boot REST API for a property rental and booking platform that combines private accommodations and hotel listings.

## Overview

StaySphere is a property rental and booking platform backend built with Spring Boot. This project focuses on **code refactoring and optimization** with an emphasis on **readability, scalability, and maintainability** through the implementation of design patterns and SOLID principles.

## Tech Stack

- **Java**: 17
- **Framework**: Spring Boot 3.4.2
- **Build Tool**: Maven
- **Database**: MongoDB Atlas
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **Containerization**: Docker & Docker Compose

### Key Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Security
- Spring Boot Starter Validation
- JJWT (JWT library) 0.11.5
- Spring Boot DevTools

## Prerequisites

Before running this project, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- MongoDB (or use MongoDB Atlas)
- Docker & Docker Compose (optional)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/StaySphere-Project.git
cd StaySphere-Project
```

### 2. Configure Environment Variables

Create a `.env` file in the project root with the following variables:

```properties
MONGODB_URI=mongodb+srv://your-username:your-password@cluster.mongodb.net/?appName=YourCluster
JWT_SECRET=your-secret-key-here
```

**Note**: Replace the MongoDB URI and JWT secret with your actual credentials.

### 3. Build and Run

#### Option A: Using Maven

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

#### Option B: Using Docker Compose

```bash
# Build and run with Docker Compose
docker-compose up --build
```

#### Option C: Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Wait for Maven to download dependencies
3. Run the `StaySphereProjectApplication.java` main class

### 4. Access the Application

- **Local**: `http://localhost:8080`
- **Docker**: `http://localhost:8081`

## Project Structure

```
src/
├── main/
│   ├── java/com/example/staySphereProject/
│   │   ├── builder/           # Builder pattern implementations
│   │   ├── config/            # Security and application configuration
│   │   ├── controllers/       # REST API endpoints
│   │   ├── converters/        # DTO converters
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── exeptions/         # Custom exceptions and handlers
│   │   ├── filter/            # JWT authentication filter
│   │   ├── models/            # Domain entities
│   │   ├── repository/        # MongoDB repositories
│   │   ├── services/          # Business logic layer
│   │   └── util/              # Utility classes
│   └── resources/
│       └── application.yml    # Application configuration
└── test/                      # Unit and integration tests
```

## API Endpoints

### Authentication
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and receive JWT token

### Listings
- `GET /listings` - Get all listings
- `GET /listings/{id}` - Get listing by ID
- `POST /listings` - Create a new listing (requires authentication)
- `PUT /listings/{id}` - Update listing (requires authentication)
- `DELETE /listings/{id}` - Delete listing (requires authentication)

### Bookings
- `GET /bookings` - Get all bookings
- `POST /bookings` - Create a new booking (requires authentication)
- `GET /bookings/user/{userId}` - Get bookings by user
- `DELETE /bookings/{id}` - Cancel booking (requires authentication)

### Reviews
- `POST /reviews` - Create a review (requires authentication)
- `GET /reviews/listing/{listingId}` - Get reviews for a listing

### Users
- `GET /users/{id}` - Get user profile
- `PUT /users/{id}` - Update user profile (requires authentication)

For detailed API documentation and example requests, see the [Postman Collection](https://documenter.getpostman.com/view/40894272/2sAYkHnHkB).

## Architecture & Design Patterns

This project emphasizes clean code architecture through the implementation of several design patterns and principles:

### Design Patterns

#### 1. Template Method Pattern
Used to handle different types of accommodations (hotels, private residences) in a unified yet flexible way. The abstract `AbstractListingProcessor` defines the template for processing listings, while concrete implementations like `ResidenceProcessor` provide specific behavior.

**Benefits**:
- Unified processing flow for different listing types
- Easy to add new accommodation types without modifying existing code
- Reduces code duplication

#### 2. Builder Pattern
Implemented in `BookingBuilder` to construct complex booking objects with optional services (e.g., pet cleaning fees, bike rentals).

**Benefits**:
- Flexible booking creation with optional add-ons
- Improved readability when creating bookings
- Type-safe construction of complex objects

### SOLID Principles

#### Single Responsibility Principle (SRP)
Each service class has a single, well-defined responsibility:
- `BookingService` - Booking management
- `ListingService` - Listing management
- `AvailabilityService` - Availability calculations
- `CostCalculationService` - Price calculations
- `DateRangeService` - Date range operations

#### Open/Closed Principle
The system is open for extension but closed for modification through the use of abstract classes:
- New listing types can be added by extending `AbstractListingProcessor`
- No need to modify existing processor code

#### DRY (Don't Repeat Yourself)
Common functionality like availability management is extracted into reusable services, eliminating code duplication across `BookingService` and `ListingService`.

## UML Diagrams

Comprehensive UML documentation is available to visualize the system architecture:

- **Class Diagram**: Overview of entities, their relationships, and responsibilities
- **Sequence Diagram**: Booking process flow
- **Use Case Diagram**: System actors and their interactions

[View UML Diagrams](https://app.diagrams.net/#G1TB9EcJWsZpQ4blyUWf9_ynuVNCXWChM9#%7B%22pageId%22%3A%22fZKKRFcfot3JoDX8hNPE%22%7D)

## Testing

Run tests using Maven:

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## API Testing with Postman

A complete Postman collection with pre-configured requests is available for easy API testing:

- Authentication examples
- CRUD operations for listings
- Booking workflows
- Review management

[View Postman Documentation](https://documenter.getpostman.com/view/40894272/2sAYkHnHkB)

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control (GUEST, HOST, ADMIN)
- Protected endpoints requiring valid JWT tokens

## Future Enhancements

- Admin panel for system maintenance
- Hotel chain management
- Enhanced review system
- Payment integration
- Real-time notifications

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is part of an academic assignment.

## Contact

For questions or feedback, please open an issue in the GitHub repository.  


