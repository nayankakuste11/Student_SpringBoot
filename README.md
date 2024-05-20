# Student Management Spring Boot Application

## Overview
This project is a Spring Boot application designed to manage student information. It provides RESTful endpoints to perform CRUD operations and various queries on student data.

## Technologies Used
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **H2 Database** (or any preferred database)
- **Jakarta Persistence API**

## Project Structure

### `com.qsp.student_springboot.dto`
Contains the `Student` class annotated with JPA annotations to map it to the database.

### `com.qsp.student_springboot.response`
Contains the `ResponseStructure` class used to structure API responses.

### `com.qsp.student_springboot.controller`
Contains the `StudentController` class defining RESTful endpoints for managing students.

### `com.qsp.student_springboot.service`
Contains the `StudentService` class handling business logic and communicating with the DAO layer.

### `com.qsp.student_springboot.dao`
Contains the `StudentDao` class interacting with the database using Spring Data JPA repository methods.

### `com.qsp.student_springboot.repo`
Contains the `StudentRepo` interface extending `JpaRepository` to provide CRUD operations and custom query methods.

## Endpoints

### StudentController

#### Save Operations
- `POST /student/save` - Save a single student.
- `POST /student/saveAll` - Save a list of students.

#### Find Operations
- `GET /student/all` - Retrieve all students.
- `GET /student/login/{userName}/{password}` - Login a student using username (email or phone) and password.
- `GET /student/findByGrade/{grade}` - Retrieve students by grade.
- `GET /student/findByFatherName/{fatherName}` - Retrieve students by father's name.
- `GET /student/findByMotherName/{motherName}` - Retrieve students by mother's name.
- `GET /student/findById/{id}` - Retrieve a student by ID.
- `GET /student/findByEmail/{email}` - Retrieve a student by email.
- `GET /student/findByName/{name}` - Retrieve students by name.
- `GET /student/findByPhone/{phone}` - Retrieve a student by phone number.
- `GET /student/findByAddress/{address}` - Retrieve students by address.
- `GET /student/lessThan/{percentage}` - Retrieve students with percentage less than the given value.
- `GET /student/greaterThan/{percentage}` - Retrieve students with percentage greater than the given value.
- `GET /student/between/{percentage1}/{percentage2}` - Retrieve students with percentage between the given values.

#### Delete Operations
- `DELETE /student/delete/{id}` - Delete a student by ID.
- `DELETE /student/delete/byEmail/{email}` - Delete a student by email.
- `DELETE /student/delete/byPhone/{phone}` - Delete a student by phone number.
- `DELETE /student/delete/all` - Delete all students.
- `DELETE /student/delete/byName/{name}` - Delete students by name.
- `DELETE /student/delete/byAddress/{address}` - Delete students by address.
- `DELETE /student/delete/byGrade/{grade}` - Delete students by grade.

#### Update Operations
- `PATCH /student/updateName/{id}/{name}` - Update student name by ID.
- `PATCH /student/updateFatherName/{id}/{name}` - Update father name by ID.
- `PATCH /student/updateMotherName/{id}/{name}` - Update mother name by ID.
- `PATCH /student/updateAddress/{id}/{address}` - Update address by ID.
- `PATCH /student/updateEmail/{id}/{email}` - Update email by ID.
- `PATCH /student/update/password/{email}/{password}` - Update password by email.
- `PATCH /student/update/password/{phone}/{password}` - Update password by phone.
- `PATCH /student/update/all` - Update all details of a student.

## Setup and Running the Application

1. **Clone the repository**
   ```sh
   git clone <repository-url>
   cd <repository-directory>
