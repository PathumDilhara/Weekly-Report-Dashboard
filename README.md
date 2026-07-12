# Weekly Report Generator & Team Dashboard

A full-stack web application for managing weekly work reports with role-based access, built as a technical assessment.

## Overview

This application allows team members to submit structured weekly reports while enabling managers to monitor team progress through a centralized dashboard.

The project follows a modern full-stack architecture using **Spring Boot**, **Next.js**, **PostgreSQL**, and an optional **FastAPI AI Assistant**.

---

# Tech Stack

## Frontend

- Next.js
- React
- TypeScript
- Tailwind CSS

## Backend

- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate

## AI Assistant (Bonus)

- FastAPI
- LangChain
- ChromaDB
- Local LLaMA Model
- Retrieval-Augmented Generation (RAG)

## Database

- PostgreSQL

---

# Features

## Authentication

- User Registration
- User Login
- JWT Authentication
- Password Encryption (BCrypt)
- Role-Based Authorization

### Roles

- Team Member
- Manager

---

## Weekly Reports

- Create Weekly Report
- Update Weekly Report
- Delete Weekly Report
- Submit Report
- View Personal Report History

Each report contains:

- Week Start
- Week End
- Project
- Tasks Completed
- Tasks Planned
- Blockers
- Hours Worked

---

## Projects

- Create Project
- Manage Projects
- Assign Reports to Projects

---

## AI Assistant (Bonus)

An AI-powered assistant built using Retrieval-Augmented Generation (RAG).

Capabilities include:

- Question answering over submitted reports
- Team report summaries
- Context-aware responses using ChromaDB

---

# Project Structure

```
weekly-report-dashboard
│
├── frontend/              # Next.js Application
│
├── backend/               # Spring Boot REST API
│
├── ai-assistant/          # FastAPI + LangChain
│
└── README.md
```

---

# Backend Architecture

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
PostgreSQL Database
```

---

# Current Implementation Status

## Completed

- User Authentication
- JWT Security
- Role-Based Authorization
- User Management
- Weekly Report Backend
- REST API
- PostgreSQL Integration
- AI Assistant Backend
- Clean Layered Architecture

## In Progress

- Team Dashboard UI
- Charts & Analytics
- Project Management UI
- Manager Dashboard
- Frontend Integration

---

# Getting Started

## Clone Repository

```bash
git clone <https://github.com/PathumDilhara/Weekly-Report-Dashboard>
```

---

# Backend Setup

## Requirements

- Java 17+
- Maven
- PostgreSQL

### Configure Database

Update `application.yml`.

```properties
spring:
    datasource:
        url:jdbc:postgresql://localhost:5432/dashboard_db
        datasource:
            username: YOUR_USERNAME
            password:YOUR_PASSWORD
```

### Run

```bash
cd backend
mvn spring-boot:run
```

Backend runs on

```
http://localhost:8080
```

---

# Frontend Setup

```bash
cd frontend

npm install

npm run dev
```

Frontend runs on

```
http://localhost:3000
```

---

# AI Assistant Setup

## Requirements

- Python 3.11+
- Conda (optional)

Install dependencies

```bash
pip install -r requirements.txt
```

Configure environment variables if required.

Run

```bash
uvicorn api.main:app --reload
```

Runs on

```
http://localhost:8000
```

---

# API Modules

- Authentication
- Users
- Weekly Reports
- Projects
- AI Assistant

---

# Database

Main entities:

- User
- Role
- WeeklyReport
- Project

---

# Future Improvements

- Complete Manager Dashboard
- Interactive Charts
- Report Analytics
- Notification System
- Docker Support
- CI/CD Pipeline
- Unit & Integration Tests
- Deployment

---

# Notes

This repository represents a work-in-progress technical assessment. While not all planned features are fully implemented, the project demonstrates the overall architecture, backend design, authentication system, REST API development, and integration of an AI-powered assistant.

---

# Author

**Pathum**
BSc (Hons) Computer Science  
University of Jaffna
