## Bookify - JavaFX + Java + MySQL

**Description:**  
A simple **JavaFX desktop application** backed by a **MySQL database** for managing users and bookings.
Supports **sign up, log in, viewing categories, and booking available slots** through visual interface.

## Tech Stack:
- Java (JDK 24)
- JavaFX (24.0.2)
- MySQL (via JDBC)
- Gradle (8.x)

## Key Features:
- User sign-up and login system  
- View available categories and time slots  
- Book or cancel slots in real time  
- Color indicators for booked and free slots  
- Data stored and managed in a MySQL database  

## Database Schema:
```bash
CREATE DATABASE booking_system;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    slot VARCHAR(50) NOT NULL,
    is_booked TINYINT(1) DEFAULT 0,
    username VARCHAR(50),
    UNIQUE (category_id, slot),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

## ⚙️ How to Run: 

1. **Clone the repository**
 ```bash
git clone https://github.com/xhibril/Bookify.git
cd Bookify
 ```
  
2. **Set up environment variables**
```bash
DB_URL=jdbc:mysql://127.0.0.1:3306/booking_system
DB_USER=root
DB_PASSWORD=your_password
```

4. **Run the application with Gradle**
```bash
gradlew run
```

