# 🎓 CampusConnect
### Campus Event Management System

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

**A comprehensive campus event management system built with JavaFX that connects students, clubs, and administrators in one unified platform.**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture) • [Contributing](#-contributing)

</div>

---

## 📋 Table of Contents

- [About](#-about)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Usage](#-usage)
- [User Roles](#-user-roles)
- [Screenshots](#-screenshots)
- [API Documentation](#-api-documentation)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 About

**CampusConnect** is a modern, feature-rich campus event management system designed to streamline the organization and participation of campus activities. Built with JavaFX and PostgreSQL, it provides a robust platform for students, club members, and administrators to collaborate effectively.

### 🌟 Why CampusConnect?

- **Centralized Management**: All campus events and clubs in one place
- **Role-based Access**: Tailored experiences for students, club members, and administrators
- **Real-time Notifications**: Stay updated on events and club activities
- **Advanced Analytics**: Export capabilities and comprehensive reporting
- **User-friendly Interface**: Intuitive JavaFX-based desktop application

---

## ✨ Features

### 🎪 Event Management
- **Create & Manage Events**: Comprehensive event creation with details, venue, timing, and capacity
- **Registration System**: Easy event registration with capacity management
- **Approval Workflow**: Admin approval system for events
- **Dynamic Pricing**: Automatic pricing adjustments based on early bird discounts and proximity to event date
- **Event History**: Complete tracking of past events with analytics

### 🏛️ Club Management
- **Club Creation**: Found new clubs with detailed descriptions and categories
- **Membership System**: Join existing clubs with role-based permissions
- **Club Approval**: Administrative approval process for new clubs
- **Member Management**: Track club members and their roles (President, Member)
- **Data Export**: CSV export functionality for club data

### 👥 User Management
- **Multi-role System**: Support for Students, Club Members, and Administrators
- **Profile Management**: Comprehensive user profiles with interests and preferences
- **Authentication**: Secure login system with role-based access control
- **Registration**: Easy signup process with validation

### 🔔 Communication
- **Notification System**: Real-time notifications for events and club updates
- **Messaging**: Integrated communication between users
- **Email Integration**: Email notifications for important updates

### 📊 Analytics & Reporting
- **Event Analytics**: Participation statistics and event performance metrics
- **Club Statistics**: Member growth and activity tracking
- **Data Export**: CSV export capabilities for administrative reporting
- **Dashboard Views**: Role-specific dashboards with relevant information

---

## 🛠️ Technology Stack

### Core Technologies
- **Java 11+**: Core programming language
- **JavaFX**: Desktop GUI framework
- **PostgreSQL**: Primary database
- **JDBC**: Database connectivity

### Architecture & Design
- **MVC Pattern**: Clean separation of concerns
- **DAO Pattern**: Data access abstraction
- **Service Layer**: Business logic encapsulation
- **FXML**: Declarative UI design

### Development Tools
- **IntelliJ IDEA**: Primary IDE
- **Scene Builder**: FXML visual editor
- **Git**: Version control

---

## 🏗️ Architecture

CampusConnect follows a layered architecture pattern ensuring maintainability and scalability:

```
┌─────────────────────────────────────────┐
│                 View Layer              │
│            (JavaFX + FXML)              │
├─────────────────────────────────────────┤
│              Controller Layer           │
│         (Event Handlers & UI Logic)     │
├─────────────────────────────────────────┤
│               Service Layer             │
│            (Business Logic)             │
├─────────────────────────────────────────┤
│                DAO Layer                │
│            (Data Access)                │
├─────────────────────────────────────────┤
│               Model Layer               │
│            (Entity Classes)             │
├─────────────────────────────────────────┤
│              Database Layer             │
│            (PostgreSQL)                 │
└─────────────────────────────────────────┘
```

### Key Components

- **Controllers**: Handle user interactions and coordinate between views and services
- **Services**: Implement business logic and coordinate between controllers and DAOs
- **DAOs**: Provide data access abstraction and database operations
- **Models**: Represent core entities (User, Event, Club, etc.)
- **Utils**: Common utilities for validation, database connection, etc.

---

## 📋 Prerequisites

Before setting up CampusConnect, ensure you have the following installed:

- **Java Development Kit (JDK) 11 or higher**
- **PostgreSQL 12 or higher**
- **JavaFX SDK 11 or higher**
- **IntelliJ IDEA** (recommended) or any Java IDE
- **Git** for version control

### System Requirements
- **OS**: Windows 10/11, macOS 10.14+, or Linux
- **RAM**: Minimum 4GB, Recommended 8GB
- **Storage**: At least 1GB free space
- **Network**: Internet connection for database setup

---

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Ansh-Patoliya/CampusConnect.git
cd CampusConnect
```

### 2. Database Setup
1. Install PostgreSQL and create a new database:
```sql
CREATE DATABASE campus_connect;
```

2. Execute the database schema:
```bash
psql -U your_username -d campus_connect -f src/Queary/database_schema.sql
```

### 3. Configure Database Connection
Update the database connection settings in `src/com/eventApp/Utils/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/campus_connect";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### 4. JavaFX Setup
1. Download JavaFX SDK from [OpenJFX](https://openjfx.io/)
2. Add JavaFX libraries to your project classpath
3. Configure VM options:
```
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

### 5. Build and Run
1. Open the project in IntelliJ IDEA
2. Configure the project SDK (Java 11+)
3. Add PostgreSQL JDBC driver to libraries
4. Run the `Main.java` class

---

## 🗄️ Database Setup

### Database Schema Overview

The system uses a comprehensive PostgreSQL schema with the following key tables:

- **users**: Core user information and authentication
- **students**: Student-specific data (department, semester, interests)
- **clubs**: Club information and status
- **club_members**: Club membership relationships
- **events**: Event details and management
- **event_registration**: Event registration tracking
- **notifications**: User notifications
- **event_history**: Historical event data

### Key Features of Database Design

1. **Referential Integrity**: Comprehensive foreign key relationships
2. **Triggers**: Automatic updates for counts and status changes
3. **Stored Procedures**: Complex business logic implemented in database
4. **Functions**: Reusable validation and calculation functions

### Sample Data Setup

Run the following to create sample data:
```sql
-- Create admin user
INSERT INTO users (user_id, name, email, password, role) 
VALUES ('admin001', 'System Admin', 'admin@campus.edu', 'hashed_password', 'ADMIN');

-- Create sample student
INSERT INTO users (user_id, name, email, password, role) 
VALUES ('STU001', 'John Doe', 'john.doe@student.edu', 'hashed_password', 'STUDENT');

INSERT INTO students (student_id, department, semester, interests) 
VALUES ('STU001', 'Computer Science', 6, 'Programming,Gaming,Music');
```

---

## 💡 Usage

### For Students
1. **Registration**: Sign up with student credentials
2. **Browse Events**: View upcoming campus events
3. **Event Registration**: Register for events with automatic pricing
4. **Club Discovery**: Find and join campus clubs
5. **Profile Management**: Update interests and preferences

### For Club Members
1. **Club Management**: Create and manage club details
2. **Event Creation**: Organize events with approval workflow
3. **Member Management**: Track club membership and roles
4. **Analytics**: View event participation and club growth

### For Administrators
1. **System Overview**: Monitor overall campus activity
2. **Approval Management**: Approve clubs and events
3. **User Management**: Manage user accounts and roles
4. **Data Export**: Generate reports and analytics
5. **System Configuration**: Configure system-wide settings

---

## 👤 User Roles

### 🎓 Student
- Browse and register for events
- Join existing clubs
- Manage personal profile and interests
- Receive notifications about events

### 🏛️ Club Member
- All student capabilities
- Create and manage events
- Manage club membership
- Export club data
- View club analytics

### 🛡️ Administrator
- System-wide oversight
- Approve/reject clubs and events
- Manage user accounts
- Generate system reports
- Configure system settings

---

## 📸 Screenshots

> **Note**: Screenshots will be added as the application is further developed and deployed.

### Login Interface
*Clean and intuitive login screen with role-based access*

### Student Dashboard
*Comprehensive view of events, clubs, and notifications*

### Event Management
*Detailed event creation and management interface*

### Club Overview
*Club details with member management capabilities*

---

## 📚 API Documentation

### Core Services

#### UserService
- `checkLogin(email, password)`: Authenticate user credentials
- `getUserByEmail(email)`: Retrieve user information
- `createUser(userData)`: Register new user

#### EventService
- `createEvent(eventData)`: Create new event
- `registerForEvent(userId, eventId)`: Register user for event
- `getEventsByClub(clubId)`: Retrieve club events

#### ClubService
- `createClub(clubData)`: Create new club
- `joinClub(userId, clubId)`: Add member to club
- `exportClubData(filename)`: Export club information

### Validation Utilities
- Email validation with uniqueness check
- Password strength validation
- Enrollment number validation
- Event timing validation

---

## 🤝 Contributing

We welcome contributions to CampusConnect! Here's how you can help:

### Getting Started
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

### Contribution Guidelines
- Follow Java coding conventions
- Write comprehensive JavaDoc comments
- Include unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting

### Areas for Contribution
- **UI/UX Improvements**: Enhance the user interface
- **Feature Development**: Add new functionality
- **Performance Optimization**: Improve application performance
- **Testing**: Expand test coverage
- **Documentation**: Improve project documentation

---

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **JavaFX Community** for the excellent desktop framework
- **PostgreSQL Team** for the robust database system
- **IntelliJ IDEA** for the powerful development environment
- **OpenJFX** for making JavaFX accessible and modern

---

## 📞 Contact & Support

For questions, suggestions, or support:

- **Project Maintainer**: [Ansh Patoliya](https://github.com/Ansh-Patoliya)
- **Issues**: [GitHub Issues](https://github.com/Ansh-Patoliya/CampusConnect/issues)
- **Discussions**: [GitHub Discussions](https://github.com/Ansh-Patoliya/CampusConnect/discussions)

---

<div align="center">

**⭐ If you find this project helpful, please consider giving it a star! ⭐**

Made with ❤️ by [Ansh Patoliya](https://github.com/Ansh-Patoliya)

</div>