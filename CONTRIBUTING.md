# Contributing to CampusConnect

First off, thank you for considering contributing to CampusConnect! It's people like you that make CampusConnect such a great tool for campus event management.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues list as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

- **Use a clear and descriptive title** for the issue
- **Describe the exact steps which reproduce the problem**
- **Provide specific examples to demonstrate the steps**
- **Describe the behavior you observed after following the steps**
- **Explain which behavior you expected to see instead and why**
- **Include screenshots and animated GIFs** which show you following the described steps

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

- **Use a clear and descriptive title** for the issue
- **Provide a step-by-step description of the suggested enhancement**
- **Provide specific examples to demonstrate the steps**
- **Describe the current behavior** and **explain which behavior you expected to see instead**
- **Explain why this enhancement would be useful** to most CampusConnect users

### Pull Requests

- Fill in the required template
- Do not include issue numbers in the PR title
- Include screenshots and animated GIFs in your pull request whenever possible
- Follow the Java coding conventions
- Include thoughtfully-worded, well-structured JavaDoc comments
- Include tests when adding new features
- Document new code based on the Documentation Styleguide
- End all files with a newline

## Development Setup

### Prerequisites

- Java JDK 11 or higher
- PostgreSQL 12 or higher
- JavaFX SDK 11 or higher
- IntelliJ IDEA (recommended)

### Setting up your development environment

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR-USERNAME/CampusConnect.git`
3. Set up the database as described in the README
4. Configure your IDE with the JavaFX modules
5. Run the application to ensure everything works

### Coding Guidelines

#### Java Code Style

- Use 4 spaces for indentation
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for all public methods and classes
- Keep methods focused and small
- Use proper exception handling

#### Database Guidelines

- Use meaningful table and column names
- Add proper indexes for performance
- Include appropriate constraints
- Document complex queries and stored procedures

#### UI Guidelines

- Follow JavaFX best practices
- Use consistent styling across components
- Ensure responsive design principles
- Maintain accessibility standards

## Testing

### Writing Tests

- Write unit tests for all new functionality
- Ensure tests are isolated and don't depend on external resources
- Use descriptive test method names
- Include both positive and negative test cases

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.eventApp.service.EventServiceTest"
```

## Documentation

### Code Documentation

- Use JavaDoc for all public classes and methods
- Include parameter descriptions and return value explanations
- Document any complex algorithms or business logic
- Keep documentation up to date with code changes

### README Updates

- Update feature lists when adding new functionality
- Include new setup steps if required
- Add new screenshots for UI changes
- Update the API documentation section

## Commit Message Guidelines

We follow conventional commit format:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that do not affect the meaning of the code
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `perf`: A code change that improves performance
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools

### Examples

```
feat(events): add event reminder notifications

fix(auth): resolve login validation issue

docs(readme): update installation instructions

style(ui): improve button hover effects
```

## Release Process

1. Update version numbers in relevant files
2. Update CHANGELOG.md with new features and fixes
3. Create a new release on GitHub
4. Tag the release with semantic versioning

## Community

- Join our [GitHub Discussions](https://github.com/Ansh-Patoliya/CampusConnect/discussions) for questions and general discussion
- Report bugs and request features through [GitHub Issues](https://github.com/Ansh-Patoliya/CampusConnect/issues)

## Recognition

Contributors will be recognized in:
- The project README
- Release notes for their contributions
- A dedicated contributors section (coming soon)

Thank you for contributing to CampusConnect! 🎉