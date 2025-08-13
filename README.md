# CampusConnect 🎓

A comprehensive campus event management system that connects students, clubs, and administrators through a unified platform for organizing, managing, and participating in campus events.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [User Roles](#user-roles)
- [Usage Guide](#usage-guide)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

CampusConnect is a JavaFX-based desktop application designed to streamline campus event management. It provides a centralized platform where students can discover and register for events, clubs can organize activities, and administrators can oversee the entire ecosystem with proper approval workflows.

### Key Highlights
- **Role-based Access Control** with three user types: Students, Club Members, and Administrators
- **Event Lifecycle Management** from creation to completion with approval workflows
- **Club Management System** with membership controls and activity tracking
- **Payment Integration** for paid events with discount systems
- **Real-time Updates** with automatic count tracking and notifications
- **Comprehensive Reporting** with event history and participation analytics

## ✨ Features

### 👨‍🎓 For Students
- **User Registration & Authentication** - Secure account creation with profile management
- **Event Discovery** - Browse events by category, date, and interest
- **Event Registration** - Register for events with payment processing
- **Club Exploration** - Discover and join campus clubs
- **Personal Dashboard** - Track registrations, club memberships, and upcoming events
- **Interest-based Recommendations** - Get personalized event suggestions

### 🏛️ For Clubs
- **Club Creation & Management** - Create clubs with detailed descriptions and categories
- **Event Organization** - Create and manage events with venue, timing, and capacity details
- **Member Management** - Handle club memberships and positions
- **Event Analytics** - Track registrations, attendance, and engagement
- **Payment Management** - Handle paid events with discount options

### 👨‍💼 For Administrators
- **Approval Workflows** - Review and approve club and event applications
- **User Management** - Oversee all user accounts and activities
- **System Analytics** - Comprehensive reporting on platform usage
- **Content Moderation** - Monitor and manage platform content

### 🔧 System Features
- **Database Integration** - PostgreSQL with triggers and stored procedures
- **Custom Data Structures** - Optimized linked lists and queues for performance
- **Exception Handling** - Robust error management and validation
- **Responsive UI** - JavaFX-based interface with multiple screen layouts

## 🛠️ Technology Stack

### Frontend
- **JavaFX** - Desktop application framework
- **FXML** - UI layout and design
- **CSS** - Styling and theming

### Backend
- **Java** - Core application logic
- **Custom Data Structures** - LinkedLists, Queues, Circular Lists
- **DAO Pattern** - Database access layer

### Database
- **PostgreSQL** - Primary database
- **Triggers & Functions** - Automated database operations
- **Stored Procedures** - Complex business logic

### Development Tools
- **IntelliJ IDEA** - Primary IDE
- **Git** - Version control

## 📋 Prerequisites

Before running CampusConnect, ensure you have the following installed:

- **Java Development Kit (JDK) 11 or higher**
- **PostgreSQL 12 or higher**
- **JavaFX SDK** (if not included with your JDK)
- **IntelliJ IDEA** (recommended) or any Java IDE

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Ansh-Patoliya/CampusConnect.git
cd CampusConnect
```

### 2. Set Up JavaFX
If JavaFX is not included with your JDK:
1. Download JavaFX SDK from [OpenJFX](https://openjfx.io/)
2. Extract and note the path
3. Add JavaFX libraries to your project classpath

### 3. Configure IDE
#### For IntelliJ IDEA:
1. Open the project (`CampusConnect.iml`)
2. Go to File → Project Structure → Libraries
3. Add JavaFX library if not automatically detected
4. Ensure PostgreSQL JDBC driver is in classpath

#### VM Options for running:
```
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

## 🗄️ Database Setup

### 1. Install PostgreSQL
Download and install PostgreSQL from [postgresql.org](https://www.postgresql.org/downloads/)

### 2. Create Database
```sql
-- Connect to PostgreSQL and create database
CREATE DATABASE CampusConnect;
```

### 3. Execute Schema
Run the SQL script located in `src/Queary/schema.sql`:
```bash
psql -U postgres -d CampusConnect -f src/Queary/schema.sql
```

### 4. Update Database Configuration
Edit `src/com/eventApp/Utils/DatabaseConnection.java`:
```java
// Update with your PostgreSQL credentials
return DriverManager.getConnection("jdbc:postgresql://localhost:5432/CampusConnect",
        "your_username", "your_password");
```

## ▶️ Running the Application

### Method 1: IDE (Recommended)
1. Open project in IntelliJ IDEA
2. Ensure all dependencies are resolved
3. Run `src/com/eventApp/Main.java`

### Method 2: Command Line
```bash
# Compile (ensure JavaFX and PostgreSQL driver in classpath)
javac -cp "path/to/javafx/lib/*:path/to/postgresql.jar" src/com/eventApp/Main.java

# Run with JavaFX modules
java --module-path path/to/javafx/lib --add-modules javafx.controls,javafx.fxml \
     -cp "src:path/to/postgresql.jar" com.eventApp.Main
```

## 📁 Project Structure

```
CampusConnect/
├── src/
│   ├── com/eventApp/
│   │   ├── Main.java                 # Application entry point
│   │   ├── Controller/               # UI Controllers (22 files)
│   │   │   ├── LoginController.java
│   │   │   ├── StudentController.java
│   │   │   └── ...
│   │   ├── Model/                    # Data Models
│   │   │   ├── User.java
│   │   │   ├── Student.java
│   │   │   ├── Club.java
│   │   │   ├── Event.java
│   │   │   └── ...
│   │   ├── Service/                  # Business Logic
│   │   │   ├── UserService.java
│   │   │   ├── ClubService.java
│   │   │   └── ...
│   │   ├── DAO/                      # Data Access Objects
│   │   │   ├── UserDAO.java
│   │   │   ├── EventDAO.java
│   │   │   └── ...
│   │   ├── FXML/                     # UI Layouts (22 files)
│   │   │   ├── Loginpage.fxml
│   │   │   ├── StudentDashboard.fxml
│   │   │   └── ...
│   │   ├── Utils/                    # Utility Classes
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── ValidationUtils.java
│   │   │   └── CurrentUser.java
│   │   ├── DataStructures/           # Custom Data Structures
│   │   │   ├── MyEventLL.java
│   │   │   ├── MyClubQueue.java
│   │   │   └── CircularLL.java
│   │   └── ExceptionHandler/         # Error Handling
│   │       ├── DatabaseExceptionHandler.java
│   │       └── ValidationException.java
│   └── Queary/                       # Database Schema
│       └── schema.sql
├── .gitignore
├── CampusConnect.iml                 # IntelliJ Project File
└── README.md
```

## 👥 User Roles

### 🎓 Student
- Register for campus events
- Join clubs and organizations
- Manage personal profile and interests
- View event history and certificates

### 🏛️ Club Member
- All student privileges
- Create and manage events
- Handle club memberships
- Access club analytics

### 👨‍💼 Administrator
- Platform-wide oversight
- Approve clubs and events
- Manage user accounts
- Access system-wide analytics

## 📖 Usage Guide

### First Time Setup
1. Launch the application
2. Click "Register" to create a new account
3. Choose your role (Student/Club Member)
4. Complete your profile information

### For Students
1. **Dashboard**: View upcoming events and club activities
2. **Browse Events**: Filter by category, date, or interests
3. **Register**: Click on events to register and pay if required
4. **Join Clubs**: Explore clubs and submit membership requests

### For Club Members
1. **Create Club**: Submit club application for admin approval
2. **Manage Events**: Create events with details, capacity, and pricing
3. **Member Management**: Handle membership requests and roles
4. **Analytics**: Track event performance and member engagement

### For Administrators
1. **Approval Queue**: Review pending clubs and events
2. **User Management**: Monitor user activities and accounts
3. **System Health**: Check platform statistics and reports

## 🤝 Contributing

We welcome contributions to CampusConnect! Here's how you can help:

### Getting Started
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Add JavaDoc comments for public methods
- Include unit tests for new features
- Ensure database migrations are included
- Update documentation as needed

### Areas for Contribution
- 🎨 UI/UX improvements
- 🔧 Performance optimizations
- 📱 Mobile responsiveness
- 🌐 Web version development
- 🔒 Security enhancements
- 📊 Advanced analytics
- 🔔 Notification system

## 📄 License

This project is developed for educational purposes. Please respect the academic integrity policies of your institution when using or contributing to this project.

## 📞 Support

For support, feature requests, or bug reports:
- Create an issue on GitHub
- Contact the development team
- Check the documentation wiki

---

**Made with ❤️ for the campus community**

*CampusConnect - Bringing campus life together, one event at a time.*