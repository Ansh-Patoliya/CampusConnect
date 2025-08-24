#!/bin/bash
# Run script for CampusConnect application

JAR_FILE="target/CampusConnect-1.0.0.jar"

echo "Running CampusConnect..."
echo "========================"

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    echo "Please run ./build.sh first to build the project."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 11+ to run the application."
    exit 1
fi

echo "Starting CampusConnect application..."
echo "JAR file: $JAR_FILE"
echo "Size: $(du -h $JAR_FILE | cut -f1)"
echo ""

# Try to run with module path first (if JavaFX runtime is available)
if [ -d "/usr/share/openjfx/lib" ]; then
    echo "Found JavaFX runtime, running with module path..."
    java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml -jar "$JAR_FILE"
else
    echo "JavaFX runtime not found in standard location."
    echo "Attempting to run with embedded JavaFX libraries..."
    java -jar "$JAR_FILE"
fi