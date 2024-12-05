# Health Appointment Management System

## Overview
The **Health Appointment Management System** is a web application built to manage doctor-patient interactions, allowing users to schedule, view, and manage appointments. It features role-based access for administrators, doctors, and patients with secure authentication using Spring Security.

---

## Features
### General
- Role-based authentication (`Admin`, `Doctor`, `Patient`).
- Secure login system with hashed passwords.

### Admin
- View all users and appointments.
- Add or delete users.
- Generate reports on appointments and users.

### Doctor
- View and manage appointments.
- Update appointment statuses.

### Patient
- Schedule appointments with doctors.
- View and update personal details.
- View scheduled appointments.

---

## Technologies Used
- **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, HTML, CSS, Bootstrap
- **Database**: MySQL
- **Other Tools**: Maven, Lombok

---

## Prerequisites
1. **Java 17** or higher installed.
2. **MySQL** installed and running.
3. **Maven** installed.

---

## Setup Instructions

### Database Configuration
1. Create a MySQL database:
   ```sql
   CREATE DATABASE health_appointment;
