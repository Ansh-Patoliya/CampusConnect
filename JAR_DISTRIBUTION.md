# CampusConnect JAR Distribution Guide

## Overview
This document provides instructions for building, distributing, and running the CampusConnect JAR file.

## JAR File Information
- **File Name**: `CampusConnect-1.0.0.jar`
- **Size**: ~9.5MB
- **Type**: Executable JAR with embedded dependencies
- **Main Class**: `com.eventApp.Main`
- **Java Version**: Requires Java 11 or higher

## Dependencies Included
The JAR file includes all necessary dependencies:
- JavaFX 17.0.2 (Controls, FXML, Graphics, Base)
- PostgreSQL JDBC Driver 42.7.4
- All FXML files and application resources

## Building the JAR File

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- Internet connection (for downloading dependencies)

### Build Methods

#### Method 1: Using Build Script (Recommended)
```bash
./build.sh
```

#### Method 2: Using Maven Directly
```bash
# Clean and build
mvn clean package

# Build without tests
mvn clean package -DskipTests
```

#### Method 3: Build with specific profile
```bash
# For production build
mvn clean package -Pproduction
```

## Running the Application

### Prerequisites for Running
- Java Runtime Environment (JRE) 11 or higher
- PostgreSQL database server (configured and running)
- Display server (for GUI applications)

### Run Methods

#### Method 1: Using Run Script (Recommended)
```bash
./run.sh
```

#### Method 2: Direct JAR Execution
```bash
java -jar target/CampusConnect-1.0.0.jar
```

#### Method 3: With Explicit JavaFX Module Path (if needed)
```bash
java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/CampusConnect-1.0.0.jar
```

## Distribution

### What to Include in Distribution
1. `CampusConnect-1.0.0.jar` - The main application
2. `run.sh` - Run script for easy execution
3. `README.md` - Application documentation
4. `database_schema.sql` - Database setup script
5. This documentation file

### Installation Instructions for End Users
1. Ensure Java 11+ is installed:
   ```bash
   java -version
   ```

2. Set up PostgreSQL database using the provided schema

3. Configure database connection in the application

4. Run the application:
   ```bash
   java -jar CampusConnect-1.0.0.jar
   ```

## Troubleshooting

### Common Issues

#### "java: command not found"
- Install Java JRE/JDK 11 or higher
- Verify installation: `java -version`

#### "NoClassDefFoundError: javafx/application/Application"
- JavaFX is not available in the system
- Use the embedded JavaFX (already included in JAR)
- Or install JavaFX runtime separately

#### "SQLException: Connection refused"
- PostgreSQL server is not running
- Check database connection settings
- Verify database credentials

#### "FXML Load Exception"
- FXML files are corrupted or missing
- Rebuild the JAR file
- Check that all FXML files are included

### Memory Settings
For large datasets or better performance:
```bash
java -Xmx2G -jar CampusConnect-1.0.0.jar
```

## Platform Compatibility

### Tested Platforms
- ✅ Linux (Ubuntu 20.04+)
- ✅ Windows 10/11
- ✅ macOS 10.14+

### Architecture Support
- ✅ x86_64 (64-bit Intel/AMD)
- ✅ ARM64 (Apple Silicon, ARM Linux)

## Build Configurations

### Development Build
```bash
mvn clean package -Pdevelopment
```

### Production Build
```bash
mvn clean package -Pproduction -DskipTests
```

### Debug Build
```bash
mvn clean package -X -e
```

## File Structure in JAR
```
CampusConnect-1.0.0.jar
├── META-INF/
│   └── MANIFEST.MF (with Main-Class: com.eventApp.Main)
├── com/eventApp/
│   ├── Main.class
│   ├── Controller/ (all controller classes)
│   ├── DAO/ (data access objects)
│   ├── Model/ (entity classes)
│   ├── Service/ (business logic)
│   ├── Utils/ (utility classes)
│   └── FXML/ (all FXML files)
├── org/postgresql/ (PostgreSQL driver)
├── javafx/ (JavaFX libraries)
└── (other dependencies)
```

## Support and Contact
For issues with the JAR file or build process:
- Check the project README.md
- Review the troubleshooting section above
- Open an issue on the project repository
- Contact the development team

---

**Last Updated**: August 2024  
**Version**: 1.0.0  
**Build System**: Maven 3.9.11  
**Java Target**: 17