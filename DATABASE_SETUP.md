# Database Setup Guide 🗄️

This guide will help you set up the PostgreSQL database for CampusConnect.

## Prerequisites

- PostgreSQL 12 or higher installed
- Basic knowledge of SQL and database administration
- Administrative access to PostgreSQL

## Installation

### Windows
1. Download PostgreSQL from [official website](https://www.postgresql.org/download/windows/)
2. Run the installer and follow the setup wizard
3. Remember the password you set for the `postgres` user
4. Ensure PostgreSQL service is running

### macOS
```bash
# Using Homebrew
brew install postgresql
brew services start postgresql

# Create a database user (optional)
createuser --interactive
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib

# Start the service
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## Database Configuration

### 1. Create Database
```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create the database
CREATE DATABASE campusconnect;

-- Create a dedicated user (recommended)
CREATE USER campusconnect_user WITH PASSWORD 'your_secure_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE campusconnect TO campusconnect_user;

-- Exit psql
\q
```

### 2. Load Database Schema

Navigate to your project directory and run:

```bash
# Option 1: Using psql command line
psql -U campusconnect_user -d campusconnect -f src/Queary

# Option 2: Using PostgreSQL admin tools
# Copy the contents of src/Queary and execute in your PostgreSQL client
```

### 3. Verify Installation

```sql
-- Connect to the database
psql -U campusconnect_user -d campusconnect

-- List all tables
\dt

-- You should see the following tables:
-- users, students, clubs, club_members, events, event_registration, payment, event_history
```

## Database Schema Overview

### Core Tables

#### Users Table
```sql
CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('STUDENT', 'CLUB_MEMBER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Students Table
```sql
CREATE TABLE students (
    student_id VARCHAR(50) PRIMARY KEY,
    department VARCHAR(100) NOT NULL,
    semester INT NOT NULL CHECK (semester BETWEEN 1 AND 8),
    interests VARCHAR(500),
    CONSTRAINT fk_student_user FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

#### Clubs Table
```sql
CREATE TABLE clubs (
    club_id SERIAL PRIMARY KEY,
    club_name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    founder_id VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    member_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Events Table
```sql
CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    club_id INT NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    event_date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    venue VARCHAR(200),
    registered_count INT DEFAULT 0,
    max_participants INT DEFAULT 0,
    discount_available BOOLEAN DEFAULT FALSE,
    approval_status VARCHAR(20) DEFAULT 'Pending',
    completion_status VARCHAR(20) DEFAULT 'Not Completed'
);
```

### Database Functions

The schema includes several custom PostgreSQL functions:

#### User Data Retrieval
```sql
-- Returns complete user data by user ID
SELECT * FROM returnUserData('user123');
```

#### Club Data Retrieval
```sql
-- Returns complete club data by club ID
SELECT * FROM returnClubData(1);
```

#### Event Data Retrieval
```sql
-- Returns complete event data by event ID
SELECT * FROM returnEventData(1);
```

#### Registration Validation
```sql
-- Check if event has available spots
SELECT checkcount(event_id);

-- Check if user is already registered
SELECT checkAlreadyRegistered('user_id', event_id);
```

### Database Triggers

#### Automatic Count Updates
- **Event Registration Count**: Automatically increments when a student registers
- **Club Member Count**: Automatically increments when a member joins

#### Data Integrity
- **Club Deletion**: Automatically removes associated members and founder
- **Cascade Deletions**: Maintains referential integrity

## Configuration in Application

### Update Database Connection

Edit `src/com/eventApp/Utils/DatabaseConnection.java`:

```java
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/campusconnect";
    private static final String USERNAME = "campusconnect_user";
    private static final String PASSWORD = "your_secure_password";
    
    // ... rest of the connection logic
}
```

### Connection Properties (Alternative)

Create a `database.properties` file:

```properties
db.url=jdbc:postgresql://localhost:5432/campusconnect
db.username=campusconnect_user
db.password=your_secure_password
db.driver=org.postgresql.Driver
```

## Testing Database Connection

### From Application
Run the main application - it will attempt to connect on startup.

### Manual Testing
```sql
-- Test basic functionality
INSERT INTO users VALUES ('test123', 'Test User', 'test@example.com', 'password', 'STUDENT');

-- Verify insertion
SELECT * FROM users WHERE user_id = 'test123';

-- Clean up
DELETE FROM users WHERE user_id = 'test123';
```

## Performance Optimization

### Recommended Indexes
```sql
-- Speed up common queries
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_events_date ON events(event_date);
CREATE INDEX idx_events_club ON events(club_id);
CREATE INDEX idx_registration_user ON event_registration(user_id);
CREATE INDEX idx_registration_event ON event_registration(event_id);
```

### Connection Pooling
For production environments, consider implementing connection pooling:

```java
// Example with HikariCP
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:postgresql://localhost:5432/campusconnect");
config.setUsername("campusconnect_user");
config.setPassword("your_secure_password");
config.setMaximumPoolSize(10);
```

## Backup and Maintenance

### Regular Backup
```bash
# Create backup
pg_dump -U campusconnect_user -d campusconnect > backup_$(date +%Y%m%d).sql

# Restore from backup
psql -U campusconnect_user -d campusconnect_new < backup_20231201.sql
```

### Database Maintenance
```sql
-- Analyze tables for query optimization
ANALYZE;

-- Vacuum to reclaim space
VACUUM;

-- Update statistics
UPDATE pg_stat_user_tables SET n_tup_ins = 0, n_tup_upd = 0, n_tup_del = 0;
```

## Troubleshooting

### Common Issues

#### Connection Refused
```bash
# Check if PostgreSQL is running
sudo systemctl status postgresql

# Check port (default 5432)
netstat -an | grep 5432
```

#### Authentication Failed
1. Check `pg_hba.conf` file
2. Verify username and password
3. Ensure user has proper privileges

#### Schema Loading Errors
1. Check file path to `src/Queary`
2. Verify PostgreSQL version compatibility
3. Run each statement individually to identify issues

#### Permission Denied
```sql
-- Grant all privileges to user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO campusconnect_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO campusconnect_user;
```

### Useful Commands

```bash
# Connect to database
psql -U campusconnect_user -d campusconnect

# List databases
\l

# List tables
\dt

# Describe table structure
\d table_name

# View current connections
SELECT * FROM pg_stat_activity;

# Check database size
SELECT pg_size_pretty(pg_database_size('campusconnect'));
```

## Security Considerations

### Password Security
- Use strong passwords for database users
- Avoid storing credentials in version control
- Consider using environment variables

### Network Security
- Configure `pg_hba.conf` for appropriate access
- Use SSL connections in production
- Limit database access to application servers only

### Data Protection
- Implement regular backups
- Use proper user privileges (principle of least privilege)
- Monitor database access logs

---

For additional help, consult the [PostgreSQL documentation](https://www.postgresql.org/docs/) or create an issue in the repository.