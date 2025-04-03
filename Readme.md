# Personal Finance Tracker - Spring Boot Application

## Overview
Personal Finance Tracker is a Spring Boot-based application designed to help users manage their personal finances. It allows users to track recurring incomes and expenses, record one-time transactions, and categorize them with custom tags. This application supports user authentication, secure password storage, and financial data management. Additionally, it offers investment tracking with real-time ticker prices updated from the Alpha Vantage API.

## Features
Track Recurring Transactions: Keep track of recurring incomes and expenses.

Record One-Time Transactions: Users can log one-time transactions and categorize them with tags.

Tagging System: Each transaction can be assigned custom tags for better categorization and reporting.

User Authentication: Spring Security and BCrypt are used to secure user authentication and password storage.

Investment Tracking: The app tracks investments and updates the price of each ticker using the Alpha Vantage API (with a free tier that allows 25 requests per day).

Database Persistence: Uses MySQL with JDBC and Spring JPA to persist transaction records, user data, and recurring transactions.

## Technology Stack
Backend: Spring Boot

Database: MySQL + Spring JPA (Hibernate)

Authentication: Spring Security, BCrypt

Frontend Rendering: Thymeleaf templates for rendering dynamic pages

API Integration: Alpha Vantage API (for ticker prices)

Other: JDBC for database connection

## Database Schema
The application uses a MySQL database to store users, transactions, recurring transactions, and investments. The following tables are created:

users: Stores user information and hashed passwords.

transactions: Stores one-time transactions with associated tags.

recurring_transactions: Stores recurring income and expense transactions.

investments: Stores details of investments and their current market prices.

## Authentication
The application uses Spring Security to authenticate users.

Passwords are stored securely as BCrypt hashes in the database.

Upon registration, users can create an account by providing a username and password.

## Endpoints
POST /register: Registers a new user.

POST /login: Logs in a user.

POST /logout: Logs out a user.

GET /dashboard: User dashboard where users can see their budget at a glance and can navigate to different pages.

GET /overview: Users can see a list of their regular and recurring transactions and a monthly budget.

GET /transactions/add: Displays a form for users to record a new One-Time Transaction

POST /transactions/add: Adds a new transaction for the authenticated user.

GET /recurring-transactions/add: Displays a form for users to record a recurring transaction

POST /recurring-transactions/add: Adds a new recurring transaction for the authenticated user.

GET /investments/add: Displays a form for recording a new stock investment

POST /investments/add: Adds a new investment for the authenticated user.

GET /investments: Retrieves the user's investments

POST /investments: Users can update the price of all stocks (Alpha Vantage API) or delete an investment record

## Future Improvements
Add more investment data features, such as crypto, tracking of TSX stocks

Add Debts

Add Bank Synchronization