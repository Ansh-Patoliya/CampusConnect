#!/bin/bash

# Script to extract all method names from DAO package

echo "=== ALL METHOD NAMES IN DAO PACKAGE ==="
echo ""

for dao_file in src/com/eventApp/DAO/*.java; do
    if [ -f "$dao_file" ]; then
        class_name=$(basename "$dao_file" .java)
        echo "=== $class_name ==="
        
        # Extract method names using more sophisticated regex
        grep -n "public.*(" "$dao_file" | \
        grep -v "//" | \
        sed 's/.*public[^(]*\([a-zA-Z][a-zA-Z0-9_]*\)(.*/\1/' | \
        sort | uniq
        
        echo ""
    fi
done

echo "=== SUMMARY ==="
echo "Total DAO files: $(ls src/com/eventApp/DAO/*.java | wc -l)"
echo "Total public methods: $(grep -h "public.*(" src/com/eventApp/DAO/*.java | grep -v "//" | wc -l)"