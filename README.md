# User Service Application

A Spring Boot application to manage users, roles, and session authorizations. This project exposes REST APIs for user CRUD operations, role assignments, and session handling.

---

## 🚀 Features

- User management (Create, Read, Update, Delete)
- Role-based access control
- Session and authorization management
- Built with Spring Boot, JPA, and follows layered architecture

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **MySQL / PostgreSQL / H2** (for dev/testing)

---

## 📦 Project Structure

```
src/main/java/com/example/userservice
│
├── controllers     # REST controllers
├── dtos            # Data Transfer Objects
├── models          # JPA entity classes
├── repositories    # Spring Data JPA interfaces
├── services        # Business logic layer
└── UserServiceApplication.java
```

---

## ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
   ```

2. **Configure the database**  
   Update `application.properties` or `application.yml` in `src/main/resources`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**  
   Visit: [http://localhost:8080](http://localhost:8080)

---

## 📡 API Endpoints

### User Management

- `GET /users/{id}` – Get user details  
- `POST /users` – Create a new user  
- `PUT /users/{id}` – Update user info  
- `DELETE /users/{id}` – Delete a user  

### Role Management

- `POST /users/{id}/roles` – Assign role(s) to a user  

---

## ✅ Testing

Unit tests are provided in `UserServiceApplicationTests` to validate application context and core functionality.

---

## 🤝 Contributing

Pull requests are welcome! Fork the repository, make your changes, and submit a PR.

---

## 📬 Contact

**Name:** Mahip Bhatt 
**Email:** mahipbhatt@gmail.com  
**GitHub:** [mahipbhatt](https://github.com/mahipbhatt)
