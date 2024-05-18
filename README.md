# Bank System Console Application

This console application simulates a basic bank system where users can create accounts, perform transactions, and manage their balances. The application is built using Java and leverages object-oriented programming principles, exception handling, and database integration.

## Table of Contents
- **Features**
- **Database Setup**
- **How to Run**
- **Usage**

## Features
- Bank Creation: Create a bank with specified transaction fees.
- Account Management: Create accounts, deposit money, withdraw money, and view account balances.
- Transaction Handling: Perform transactions between accounts, including flat fee and percentage fee transactions.
- Database Integration: Data is stored in a MySQL database for persistence.
- Exception Handling: Proper error messages are displayed for various scenarios.
  
## Database Setup
- Create a MySQL database named banksystem.
- Run the provided SQL scripts to create the necessary tables: Account, Bank and Transaction.
Ensure your MySQL server is running and accessible.

## How to Run
- Clone or download the project files to your local machine.
- Open the project in your preferred Java IDE.
- Configure the database connection details in the DatabaseConnection class.
- Build and run the Main class to start the application.

## Usage
Upon starting the application, you'll be prompted to enter the flat fee and percent fee for the bank.
You can then choose from various options in the bank menu to perform different actions:
  - Create account
  - Deposit money
  - Withdraw money
  - Transfer money between accounts
  - View account balances
  - View all accounts
  - View total transaction fees
  - View total transfer amount
  - Exit the application
Follow the on-screen prompts to interact with the application and manage bank operations.
