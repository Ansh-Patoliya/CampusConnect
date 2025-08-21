# CampusConnect - Javadoc Documentation

## Overview

This document describes how to generate and view the API documentation for the CampusConnect Campus Event Management System.

## Generated Documentation

The Javadoc documentation has been generated for the core packages of the CampusConnect system:

- **Model**: Entity classes representing core business objects
- **DAO**: Data Access Objects for database operations
- **Service**: Business logic and workflow management
- **Utils**: Utility classes for common operations
- **DataStructures**: Custom data structures for optimized operations
- **ExceptionHandler**: Custom exception classes

## Viewing Documentation

1. **Local Viewing**: Open `docs/index.html` in a web browser
2. **Online**: The documentation can be hosted on any web server

## Generating Documentation

### Automatic Generation

Use the provided script for easy documentation generation:

```bash
./generate-javadoc.sh
```

### Manual Generation

To manually generate the documentation, use the following command:

```bash
javadoc -d docs \
        -sourcepath src \
        -windowtitle "CampusConnect API Documentation" \
        -doctitle "CampusConnect - Campus Event Management System" \
        -header "<b>CampusConnect</b>" \
        -use \
        -splitindex \
        -overview src/overview.html \
        com.eventApp.Model \
        com.eventApp.DAO \
        com.eventApp.Service \
        com.eventApp.Utils \
        com.eventApp.DataStructures \
        com.eventApp.ExceptionHandler
```

## Documentation Structure

The generated documentation includes:

- **Package Overview**: Comprehensive description of each package's purpose
- **Class Documentation**: Detailed class descriptions with usage examples
- **Method Documentation**: Parameter descriptions, return values, and exceptions
- **Cross-References**: Links between related classes and methods
- **Search Functionality**: Full-text search across all documentation
- **Index**: Alphabetical listing of all classes and methods

## Documentation Coverage

### Well-Documented Packages
- ✅ **DataStructures**: Complete documentation with usage examples
- ✅ **Utils**: Comprehensive utility method documentation
- ✅ **DAO**: Database operation documentation with parameter details
- ✅ **Service**: Business logic documentation with workflow descriptions
- ✅ **ExceptionHandler**: Exception handling documentation

### Partially Documented
- ⚠️ **Model**: Class-level documentation complete, some getters/setters lack individual docs
- ⚠️ **Controller**: UI controllers excluded from documentation due to JavaFX dependencies

## Requirements

- Java 17 or higher
- Source code with proper Javadoc comments
- Web browser for viewing documentation

## Notes

- The Controller package is not included in the generated documentation due to JavaFX dependency issues
- Some getter/setter methods may show warnings for missing documentation, but the core functionality is well documented
- The documentation is optimized for the core business logic and data access layers

## Maintenance

To keep documentation current:

1. Add Javadoc comments to new classes and methods
2. Update package-info.java files when adding new packages
3. Regenerate documentation after significant changes
4. Review and update the overview.html file as the system evolves

## Browser Compatibility

The generated documentation is compatible with all modern web browsers including:
- Chrome/Chromium
- Firefox
- Safari
- Edge