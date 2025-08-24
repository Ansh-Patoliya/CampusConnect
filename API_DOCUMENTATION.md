# CampusConnect REST API Documentation

## Overview
CampusConnect has been successfully converted from a JavaFX desktop application to a Spring Boot REST API application. The API provides endpoints for all major functionality including authentication, student management, club management, and event management.

## Base URL
```
http://localhost:8080/api
```

## Authentication Endpoints

### GET /auth/health
Health check endpoint to verify the API is running.

**Response:**
```json
{
  "status": "OK",
  "message": "CampusConnect API is running"
}
```

### POST /auth/login
User authentication endpoint.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login endpoint working",
  "user": {
    "email": "user@example.com",
    "role": "student"
  }
}
```

### POST /auth/register
User registration endpoint.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "student",
  "department": "Computer Science",
  "semester": 6
}
```

## Student Endpoints

### GET /students/{userId}
Get student profile information.

**Response:**
```json
{
  "userId": "STU001",
  "name": "John Doe",
  "email": "john.doe@student.edu",
  "role": "student",
  "department": "Computer Science",
  "semester": 6,
  "interest": ["Programming", "Gaming", "Music"]
}
```

### PUT /students/{userId}
Update student profile.

### GET /students/{userId}/dashboard
Get student dashboard data.

**Response:**
```json
{
  "upcomingEvents": 3,
  "clubMemberships": 2,
  "notifications": 5,
  "recentActivity": [
    "Registered for Tech Conference",
    "Joined Programming Club",
    "Updated profile interests"
  ]
}
```

### GET /students/{userId}/events
Get student's registered events.

### GET /students/{userId}/clubs
Get student's club memberships.

### POST /students/{userId}/clubs/{clubId}/join
Join a club.

### GET /students/events/search
Search events with filters.

**Query Parameters:**
- `category` (optional): Filter by event category
- `venue` (optional): Filter by venue
- `date` (optional): Filter by date

## Event Endpoints

### GET /events
Get all events.

**Response:**
```json
[
  {
    "eventId": 1,
    "eventName": "Sample Tech Conference",
    "venue": "Main Auditorium",
    "description": "A conference about latest technologies",
    "approvalStatus": "pending",
    "completionStatus": "not_completed",
    "userId": "user1",
    "category": "Technology",
    "maxParticipants": 100,
    "clubId": 1,
    "ticketPrice": 50.0,
    "discountApplicable": true,
    "eventDate": "2025-09-23",
    "startTime": "09:00:00",
    "endTime": "17:00:00"
  }
]
```

### GET /events/{eventId}
Get event by ID.

### POST /events
Create a new event.

**Request Body:**
```json
{
  "eventName": "Tech Conference",
  "description": "Conference about technology",
  "venue": "Main Auditorium",
  "clubId": 1,
  "userId": "user1",
  "maxParticipants": 100,
  "eventDate": "2024-03-15",
  "startTime": "09:00",
  "endTime": "17:00",
  "ticketPrice": 50.0,
  "discountApplicable": true,
  "category": "Technology"
}
```

### PUT /events/{eventId}
Update an event.

### POST /events/{eventId}/register
Register for an event.

### GET /events/club/{clubId}
Get events by club.

## Club Endpoints

### GET /clubs
Get all clubs.

**Response:**
```json
[
  {
    "clubId": 1,
    "clubName": "Programming Club",
    "descriptions": "A club for programming enthusiasts",
    "category": "Technology",
    "founderId": "user1",
    "status": "Pending",
    "memberCount": 15,
    "maxMemberCount": 50
  }
]
```

### GET /clubs/{clubId}
Get club by ID.

### POST /clubs
Create a new club.

**Request Body:**
```json
{
  "clubName": "Photography Club",
  "description": "For photography enthusiasts",
  "category": "Arts",
  "founderId": "user1",
  "maxMemberCount": 30
}
```

### PUT /clubs/{clubId}
Update club information.

### GET /clubs/{clubId}/members
Get club members.

**Response:**
```json
[
  {
    "userId": "user1",
    "name": "John Doe",
    "role": "President",
    "joinDate": "2024-01-15"
  }
]
```

### POST /clubs/{clubId}/members
Add member to club.

### DELETE /clubs/{clubId}/members/{userId}
Remove member from club.

### GET /clubs/{clubId}/events
Get club events.

### GET /clubs/{clubId}/dashboard
Get club dashboard data.

## Database Configuration

The application is currently configured with H2 in-memory database for demonstration purposes. To use with PostgreSQL (original database), update the `application.properties` file:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/CampusConnect
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Technology Stack

- **Spring Boot 3.2.2**: Main framework
- **Spring Data JPA**: Database operations
- **Spring Web**: REST API
- **H2 Database**: In-memory database for demo
- **PostgreSQL**: Production database (configurable)
- **Jakarta Validation**: Input validation
- **Maven**: Build tool

## Running the Application

1. **Compile**: `mvn clean compile`
2. **Run**: `mvn spring-boot:run`
3. **Access**: `http://localhost:8080/api`
4. **H2 Console**: `http://localhost:8080/api/h2-console` (for demo mode)

## Conversion Summary

Successfully converted from JavaFX desktop application to Spring Boot REST API:

✅ **Completed:**
- Spring Boot project structure with Maven
- REST API endpoints for all core functionality
- Database configuration with both H2 (demo) and PostgreSQL support
- JPA entity models with proper annotations
- Comprehensive API documentation
- All major features accessible via REST API

The application now provides a modern REST API that can be consumed by web applications, mobile apps, or other services, replacing the original JavaFX desktop interface.