#!/bin/bash
# Build script for CampusConnect JAR file

echo "Building CampusConnect JAR file..."
echo "=================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven to build the project."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 11+ to build the project."
    exit 1
fi

# Display Java version
echo "Using Java version:"
java -version

echo ""
echo "Building the project..."

# Clean and compile the project
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed!"
    exit 1
fi

# Package the JAR file
mvn package

if [ $? -ne 0 ]; then
    echo "Error: Packaging failed!"
    exit 1
fi

echo ""
echo "Build completed successfully!"
echo "Generated JAR file: target/CampusConnect-1.0.0.jar"
echo "JAR file size: $(du -h target/CampusConnect-1.0.0.jar | cut -f1)"
echo ""
echo "To run the application:"
echo "java -jar target/CampusConnect-1.0.0.jar"
echo ""
echo "Note: JavaFX runtime modules are required to run the application."
echo "Make sure JavaFX is properly installed on the target system."