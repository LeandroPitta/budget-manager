# Budget Manager

Manage your budget efficiently.

## Project Description

The Budget Manager is a comprehensive application designed to help users manage their finances effectively. The project is built using a modern tech stack, including Java with Spring Boot for the backend, Angular with TypeScript for the frontend, and PostgreSQL as the database. The application follows a multi-tier architecture, ensuring separation of concerns and scalability.

### Backend

The backend is developed using Spring Boot, which provides a robust and scalable framework for building Java applications. It leverages Spring Data JPA for database interactions and Spring Web for building RESTful APIs. The backend is responsible for handling business logic, data processing, and communication with the PostgreSQL database.

Key components:
- **Controllers**: Handle HTTP requests and map them to appropriate service methods.
- **Services**: Contain business logic and interact with repositories.
- **Repositories**: Interface with the database using Spring Data JPA.
- **Entities**: Represent the data model and are mapped to database tables.

### Frontend

The frontend is built using Angular, a powerful framework for building dynamic and responsive web applications. It uses TypeScript for type safety and better code maintainability. The frontend communicates with the backend via RESTful APIs to perform CRUD operations and display data to the user.

Key components:
- **Components**: Represent the UI elements and handle user interactions.
- **Services**: Manage communication with the backend APIs.
- **Modules**: Organize the application into cohesive blocks of functionality.
- **Routing**: Manages navigation between different views.

### Database

The application uses PostgreSQL as the database management system. It stores all the financial data, including user information, transactions, budgets, and categories. The database schema is designed to ensure data integrity and efficient querying.

## Tech Stack

<img alt="My Skills" src="https://skillicons.dev/icons?i=java,spring,angular,ts,postgres">

## Prerequisites
- **Node.js** (version 18.17.1)
- **npm** (version 9.6.7)
- **Java JDK** (version 17)
- **Maven** (version 3.8.1)
- **PostgreSQL** (version 13)
- **Angular CLI** (version 16.2.12)

## Getting Started

### Backend
1. **Install Dependencies**: 
   - Navigate to the root directory and run:
     ```bash
     mvn install
     ```
2. **Environment Variables** (Optional): 
   - Copy the `application.properties` file and configure your database connection.
3. **Database Setup** (Optional): 
   - Ensure that PostgreSQL is running and properly configured.
   - *Note: Steps 2 and 3 are not mandatory if you choose to use the pre-configured database.*
4. **Start Backend**: 
   ```bash
   mvn spring-boot:run
   ```

### Frontend
1. **Install Dependencies**: 
   - Navigate to the `angular-app` directory and run:
     ```bash
     npm install
     ```
2. **Start Frontend**:
   ```bash
   ng serve
   ```

### Optional: Using Docker and Dev Container
1. **Prerequisites**:
   - Ensure Docker is installed on your machine.
   - Install the Dev Container extension for your IDE (VS Code or IntelliJ Ultimate).

2. **Open Project in Container**:
   - Open your IDE without a project.
      - Use the command palette (VS Code: F1, IntelliJ: Ctrl+Shift+A) and select "Dev Containers: Clone Repository in Container Volume".
      - Enter the repository URL and follow the prompts to clone and open the project in a dev container.

3. **Start Backend**: 
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Start Frontend**: 
   - Navigate to the `angular-app` directory and run:     
   ```bash
   npm ci
   ng serve
   ```

## Help
For more help, refer to:
- [Angular CLI Overview and Command Reference](https://angular.io/cli)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

