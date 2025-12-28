## Project 1: Contact Book - Java + MySQL

**Description:**  
A simple **Java console application** that connects to a **MySQL database** to manage personal contacts.  
Supports creating, viewing, searching, and deleting.

## Tech Stack:
-  Java (JDK 24)
-  MySQL (via JDBC)
-  IntelliJ IDEA

## Key Features:
- Add new contacts (name, phone, email)
- View all saved contacts
- Search contacts by ID or name
- Delete contacts by ID or name
- Uses `PreparedStatement` for SQL safety
- Database credentials handled through environment variables

## Database Schema:
```bash
CREATE DATABASE contact_book;

CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(50)
);
```

## ⚙️ How to Run: 

1. **Clone the repository**
```bash
git clone https://github.com/xhibril/miniProjects.git
cd miniProjects/ContactBook
```

2. **Set up environment variables**
```bash
DB_URL=jdbc:mysql://localhost:3306/contact_book
DB_USER=root
DB_PASSWORD=your_password
```


