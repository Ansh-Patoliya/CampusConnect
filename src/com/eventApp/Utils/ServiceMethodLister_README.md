# Service Method Lister Utility

This utility provides a way to list all method names in the service package (`com.eventApp.Service`).

## Features

- **Complete Service Analysis**: Lists all public methods from all service classes
- **Multiple Output Formats**: Supports detailed view, simple list, and unique method names
- **Automatic Discovery**: Uses reflection to dynamically discover service classes
- **Organized Output**: Methods are grouped by service class and sorted alphabetically

## Usage

### From Command Line

1. **Detailed View** (default) - Shows methods organized by service class:
   ```bash
   java -cp src com.eventApp.Utils.ServiceMethodLister
   ```

2. **Simple List** - Shows all method names in a numbered list:
   ```bash
   java -cp src com.eventApp.Utils.ServiceMethodLister list
   ```

3. **Unique Methods** - Shows only unique method names (removes duplicates):
   ```bash
   java -cp src com.eventApp.Utils.ServiceMethodLister unique
   ```

### From Code

```java
import com.eventApp.Utils.ServiceMethodLister;
import java.util.Map;
import java.util.List;

// Get all methods organized by service class
Map<String, List<String>> serviceMethodsMap = ServiceMethodLister.getAllServiceMethods();

// Get a flat list of all method names
List<String> allMethods = ServiceMethodLister.getAllMethodNames();

// Get unique method names only
List<String> uniqueMethods = ServiceMethodLister.getUniqueMethodNames();

// Print formatted output
ServiceMethodLister.printAllServiceMethods();
ServiceMethodLister.printMethodNamesOnly();
ServiceMethodLister.printUniqueMethodNames();
```

## Current Service Classes

The utility currently discovers and analyzes the following service classes:

- **AdminService** - Admin-related operations
- **ClubApprovalService** - Club approval workflow
- **ClubMemberService** - Club member operations  
- **ClubService** - Club management operations
- **EventRegistrationService** - Event registration functionality
- **EventService** - Event management operations
- **StudentService** - Student-related operations
- **UserService** - User management operations

## Sample Output

### Detailed View
```
=== SERVICE PACKAGE METHOD LIST ===

📋 AdminService (4 methods):
   ────────────────────────────────
   • exportClubData()
   • getAdmin()
   • getAllClubs()
   • getClubMemberList()

📋 ClubService (11 methods):
   ───────────────────────────────
   • addEvent()
   • cancelEvent()
   • exportClubMembersToCSV()
   ...
```

### Simple List
```
=== ALL SERVICE METHOD NAMES ===
1. addEvent()
2. approveEvent()
3. approveNextClub()
...
Total Methods: 41
```

### Unique Methods
```
=== UNIQUE SERVICE METHOD NAMES ===
1. addEvent()
2. approveEvent()
3. approveNextClub()
...
Total Unique Methods: 38
```

## Technical Details

- Uses Java reflection to discover service classes at runtime
- Filters out inherited Object methods (equals, hashCode, toString, etc.)
- Only includes public methods in the output
- Automatically sorts method names alphabetically
- Handles compilation and class loading errors gracefully

## Requirements

- Java 8 or higher
- Compiled service classes in the classpath
- Service classes must be in the `com.eventApp.Service` package