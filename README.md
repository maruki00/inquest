# Log Security Analyzer

A Java-based application for parsing and analyzing HTTP access logs to detect potential security threats such as brute force attacks, SQL injection, DDoS attempts, and other malicious activities.

## Features

- Parses Apache/Nginx-style HTTP access logs
- Detects various attack patterns using configurable templates:
  - Basic analysis
  - Brute force detection
  - SQL injection detection
  - DDoS attack identification
- In-memory event storage and aggregation
- Configurable via environment variables (.env file)
- Domain-driven design architecture

## Prerequisites

- Java 25 or higher
- Maven 3.6+

## Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repo-folder>
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Install dependencies:
   ```bash
   mv .env.example .env
   edit .env vars
   ```

## Usage

1. Place your `access.log` file in the `src/main/resources/` directory.

2. Create a `.env` file in the project root with any required environment variables (if needed).

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="userGateway.Main"
   ```

The application will parse the log file and analyze it for security threats using the built-in templates.

## Project Structure

- `src/main/java/app/` - Application services and main App class
- `src/main/java/domain/` - Domain layer with entities, aggregates, and contracts
- `src/main/java/infra/` - Infrastructure layer with repositories and analysis templates
- `src/main/java/userGateway/` - Entry point (Main class)
- `src/main/resources/` - Resource files (e.g., access.log)

## Dependencies

- [dotenv-java](https://github.com/cdimascio/java-dotenv) - For loading environment variables

## License

See LICENSE file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## TODO

- Implement dynamic template loading
- Add command-line argument support
- Enhance analysis templates
- Add reporting/output options</content>
<parameter name="filePath">/home/user/dev/lab2/README.md