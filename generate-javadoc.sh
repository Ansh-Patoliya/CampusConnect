#!/bin/bash

# Script to generate Javadoc documentation for CampusConnect project
# Author: Generated for CampusConnect project
# Description: Generates comprehensive API documentation for the core packages

echo "Generating Javadoc documentation for CampusConnect..."

# Clean existing documentation
if [ -d "docs" ]; then
    echo "Removing existing documentation..."
    rm -rf docs
fi

# Create docs directory
mkdir -p docs

# Generate Javadoc for core packages (non-UI)
echo "Generating documentation for core packages..."
javadoc -d docs \
        -sourcepath src \
        -windowtitle "CampusConnect API Documentation" \
        -doctitle "CampusConnect - Campus Event Management System" \
        -header "<b>CampusConnect</b>" \
        -footer "<b>CampusConnect Event Management System</b>" \
        -use \
        -splitindex \
        -overview src/overview.html \
        com.eventApp.Model \
        com.eventApp.DAO \
        com.eventApp.Service \
        com.eventApp.Utils \
        com.eventApp.DataStructures \
        com.eventApp.ExceptionHandler

if [ $? -eq 0 ]; then
    echo "Javadoc generation completed successfully!"
    echo "Documentation is available in the 'docs' directory"
    echo "Open docs/index.html in a web browser to view the documentation"
else
    echo "Error: Javadoc generation failed"
    exit 1
fi