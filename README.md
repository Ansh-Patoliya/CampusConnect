# CampusConnect 🎓

A comprehensive JavaFX-based campus event management system that connects students, clubs, and administrators in a unified platform for organizing and participating in campus activities.

## 🌟 Features

### For Students
- **Event Discovery**: Browse and search upcoming campus events
- **Easy Registration**: Register for events with integrated payment processing
- **Club Exploration**: Discover and join campus clubs based on interests
- **Personal Dashboard**: Track your event participations and club memberships
- **Profile Management**: Maintain your academic and personal information

### For Club Members
- **Event Creation**: Create and organize events for your club
- **Member Management**: Manage club membership and roles
- **Event Analytics**: Track event attendance and engagement
- **Club Dashboard**: Overview of club activities and member statistics

### For Administrators
- **Approval Workflows**: Approve new clubs and events
- **User Management**: Oversee student and club member accounts
- **System Analytics**: Monitor platform usage and activity
- **Content Moderation**: Ensure appropriate content and activities

## 🛠️ Technology Stack

- **Frontend**: JavaFX with FXML for modern desktop UI
- **Backend**: Java with custom data structures and algorithms
- **Database**: PostgreSQL with custom functions and triggers
- **Architecture**: MVC (Model-View-Controller) pattern
- **Build Tool**: IntelliJ IDEA project structure

## 📋 Prerequisites

Before running CampusConnect, ensure you have the following installed:

- **Java 11 or higher** - [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **JavaFX SDK** - [Download JavaFX](https://openjfx.io/)
- **PostgreSQL 12 or higher** - [Download PostgreSQL](https://www.postgresql.org/download/)
- **IntelliJ IDEA** (recommended) - [Download IntelliJ](https://www.jetbrains.com/idea/)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/Ansh-Patoliya/CampusConnect.git
cd CampusConnect
```

### 2. Database Setup
1. Install and start PostgreSQL
2. Create a new database:
   ```sql
   CREATE DATABASE campusconnect;
   ```
3. Run the database schema:
   ```bash
   psql -U your_username -d campusconnect -f src/Queary
   ```

### 3. Configure Database Connection
Update the database connection settings in:
```
src/com/eventApp/Utils/DatabaseConnection.java
```

### 4. JavaFX Setup
1. Download JavaFX SDK
2. In IntelliJ IDEA:
   - Go to `File > Project Structure > Libraries`
   - Add JavaFX lib folder as a library
   - Add VM options: `--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml`

### 5. Run the Application
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp . com.eventApp.Main
```

Or run directly from IntelliJ IDEA by executing the `Main.java` file.

## 📁 Project Structure

```
CampusConnect/
├── src/
│   ├── com/eventApp/
│   │   ├── Controller/          # UI Controllers for FXML
│   │   ├── DAO/                 # Data Access Objects
│   │   ├── DataStructures/      # Custom data structures
│   │   ├── ExceptionHandler/    # Custom exceptions
│   │   ├── FXML/               # UI layout files
│   │   ├── Loader/             # Resource loaders
│   │   ├── Model/              # Entity classes
│   │   ├── Service/            # Business logic
│   │   ├── Utils/              # Utility classes
│   │   └── Main.java           # Application entry point
│   └── Queary                  # Database schema and functions
├── CampusConnect.iml           # IntelliJ module file
└── .gitignore                  # Git ignore rules
```

## 🎯 Usage Guide

### First Time Setup
1. **Admin Account**: The system creates a default admin account
2. **Database**: Ensure PostgreSQL is running and schema is loaded
3. **Launch**: Run the application and you'll see the login screen

### User Roles

#### Student Workflow
1. **Registration**: Create a student account with department and semester
2. **Browse Events**: View upcoming events filtered by interest/category
3. **Join Clubs**: Find and request membership to clubs
4. **Register for Events**: Book seats for events (with payment if required)
5. **Track Participation**: View your event history and upcoming events

#### Club Member Workflow
1. **Club Creation**: Request to create a new club (requires admin approval)
2. **Event Planning**: Create events with details, venue, and pricing
3. **Member Management**: Accept/reject membership requests
4. **Event Management**: Monitor registrations and manage event details

#### Admin Workflow
1. **Approve Clubs**: Review and approve new club requests
2. **Approve Events**: Ensure events meet guidelines before publication
3. **User Management**: Monitor user accounts and resolve issues
4. **System Monitoring**: Overview of platform activity and statistics

## 🗄️ Database Schema

The application uses PostgreSQL with the following main entities:

- **Users**: Base user information with role-based access
- **Students**: Extended student information (department, semester, interests)
- **Clubs**: Club details with approval status and membership count
- **Club_Members**: Many-to-many relationship between users and clubs
- **Events**: Event details with approval and completion status
- **Event_Registration**: Student event registrations
- **Payment**: Payment records for paid events
- **Event_History**: Historical record of completed events

### Key Features
- **Custom Functions**: Database functions for data retrieval and validation
- **Triggers**: Automatic updates for counts and relationships
- **Stored Procedures**: Complex operations like club deletion
- **Data Integrity**: Foreign key constraints and check constraints

## 🔧 Development

### Custom Data Structures
The application implements custom data structures for efficiency:
- **MyEventLL**: Linked list for event management
- **MyClubQueue**: Queue implementation for club operations
- **CircularLL**: Circular linked list for specific use cases

### Exception Handling
- Custom exception classes for database and validation errors
- Centralized error handling for user-friendly messages

### Validation
- Input validation utilities for forms and data entry
- Database-level constraints for data integrity

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Development Setup
1. Fork the repository
2. Create a feature branch
3. Follow the existing code style and patterns
4. Add tests for new functionality
5. Submit a pull request

## 📚 Additional Documentation

- [Database Setup Guide](DATABASE_SETUP.md) - Detailed database configuration
- [User Guide](USER_GUIDE.md) - Comprehensive user manual
- [Architecture Guide](ARCHITECTURE.md) - System design and patterns
- [Contributing Guidelines](CONTRIBUTING.md) - Development workflow

## 🐛 Troubleshooting

### Common Issues

1. **JavaFX Not Found**
   - Ensure JavaFX is properly configured in your IDE
   - Check VM options include JavaFX modules

2. **Database Connection Failed**
   - Verify PostgreSQL is running
   - Check database credentials in DatabaseConnection.java
   - Ensure database schema is properly loaded

3. **FXML Loading Errors**
   - Check FXML file paths in controllers
   - Ensure FXML files are in the correct resources directory

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Check existing documentation and guides
- Review troubleshooting section above

## 📄 License

This project is open source. Please refer to the repository for license details.

---

**CampusConnect** - Connecting campus communities through technology 🌟