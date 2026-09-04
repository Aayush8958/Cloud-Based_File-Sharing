
# Cloud-Based File Sharing System

A backend application built with Spring Boot that allows users to securely upload, manage, and share files. The project is inspired by services like **Send Anywhere** and is being developed to explore backend development, authentication, file management, and AWS cloud services.

The application is currently being developed and tested locally using Postman. Once the core functionality is complete, local file storage will be replaced with Amazon S3, the database will be migrated to Amazon RDS, and the application will be deployed on Amazon EC2.

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA (Hibernate)
* MySQL
* Maven
* Postman

## Features

* User registration and login
* JWT-based authentication
* Secure file upload and download
* File management
* Temporary share links
* QR code generation for file sharing
* One active share link per file
* Link expiration

## AWS Services (Planned)

* Amazon EC2
* Amazon S3
* Amazon RDS (MySQL)
* AWS IAM

## Project Structure

```text
src
├── auth
├── user
├── file
├── share
├── security
├── config
└── exception
```

## Roadmap

* [x] Project setup
* [x] User module
* [x] JWT Authentication
* [x] File upload
* [x] File download
* [ ] File sharing
* [ ] QR code generation
* [ ] Link expiration
* [ ] Amazon S3 integration
* [ ] Amazon RDS migration
* [ ] Deploy on Amazon EC2

## Notes

This project is being built with a modular architecture so that local storage can be replaced with Amazon S3 without affecting the rest of the application. The primary goal is to gain hands-on experience with Spring Boot, REST APIs, authentication, and AWS cloud services while building a practical file-sharing application.

