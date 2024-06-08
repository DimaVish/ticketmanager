Based on the extracted files, here's a comprehensive README file for your Java Spring Boot application managing a ticket system:

---

# Ticket Management System

This repository contains a Java Spring Boot application that manages a CRUD API for a ticket system. The system supports multiple user roles, including Admin, Fiscal, and Passenger. Key features include purchasing tickets, using tickets for travel, issuing fines, and paying fines.

## Features

- **Admin**
  - Manage users (create, update, delete)
  - View all tickets and transactions

- **Fiscal**
  - Issue fines to passengers
  - View and manage fines

- **Passenger**
  - Purchase tickets
  - Use tickets for travel
  - Pay fines

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **REST API**

## Prerequisites

- Java 17 or higher
- Gradle
- MySQL

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/ticket-management-system.git
    cd ticket-management-system
    ```

2. Configure the application:
    - Update the `application.yaml` file with your MySQL, Redis, and Kafka configurations.

    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/ticket_system
        username: root
        password: yourpassword
      redis:
        host: localhost
        port: 6379
      kafka:
        bootstrap-servers: localhost:9092
    ```

3. Build the application:
    ```sh
    ./gradlew build
    ```

4. Run the application:
    ```sh
    ./gradlew bootRun
    ```

## API Endpoints

### Admin Endpoints

- **Create User**
  - `POST /api/admin/users`
  - Request Body: `{ "username": "user", "password": "pass", "role": "role" }`

- **Update User**
  - `PUT /api/admin/users/{id}`
  - Request Body: `{ "username": "user", "password": "pass", "role": "role" }`

- **Delete User**
  - `DELETE /api/admin/users/{id}`

- **View All Tickets**
  - `GET /api/admin/tickets`

### Fiscal Endpoints

- **Issue Fine**
  - `POST /api/fiscal/fines`
  - Request Body: `{ "ticketId": "ticketId", "fineAmount": "amount" }`

- **View Fines**
  - `GET /api/fiscal/fines`

### Passenger Endpoints

- **Purchase Ticket**
  - `POST /api/passenger/tickets`
  - Request Body: `{ "travelDate": "YYYY-MM-DD", "destination": "destination" }`

- **Use Ticket**
  - `PUT /api/passenger/tickets/use/{ticketId}`

- **Pay Fine**
  - `POST /api/passenger/fines/pay`
  - Request Body: `{ "fineId": "fineId", "amount": "amount" }`

## Usage

After running the application, you can interact with the API using tools like Postman or cURL. You can also integrate the front-end HTML pages and JavaScript for a more user-friendly interface.

## Project Structure

- **src/main/java/com/smartticket/ticketmanager**
  - Contains the main application and configuration files
  - `controller` package includes all the controllers for handling HTTP requests
  - `dto` package includes Data Transfer Objects
  - `model` package includes JPA entities
  - `repository` package includes Spring Data JPA repositories
  - `service` package includes service layer classes
  - `security` package includes security configurations and filters

- **src/main/resources**
  - Contains application configuration files

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Feel free to customize this README file according to your specific project needs.
