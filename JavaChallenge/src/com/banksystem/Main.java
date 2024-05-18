package com.banksystem;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Using try-with-resources to ensure the scanner is closed
        try (Scanner scanner = new Scanner(System.in)) {
            // Check if the bank already exists in the database
            String bankName = "BKT";
            Bank existingBank = Bank.getBank(bankName);
            Bank bank;

            if (existingBank == null) {
                // If the bank doesn't exist, prompt the user to enter flat fee and percent fee
                System.out.print("Enter flat fee amount: ");
                double flatFee = scanner.nextDouble();
                System.out.print("Enter percent fee amount: ");
                double percentFee = scanner.nextDouble();

                // Create the bank in the database
                bank = new Bank(bankName, flatFee, percentFee);
                try {
                    bank.saveToDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error creating the bank in the database.");
                    return; // Exit the program if bank creation fails
                }
            } else {
                bank = existingBank; // Assign existingBank to bank if it already exists
            }

            while (true) {
                System.out.println("\nBank Menu:");
                System.out.println("1. Create account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. View account balance");
                System.out.println("6. View all accounts");
                System.out.println("7. View total transaction fees");
                System.out.println("8. View total transfer amount");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter account ID: ");
                            String accountId = scanner.nextLine();
                            System.out.print("Enter user name: ");
                            String userName = scanner.nextLine();
                            System.out.print("Enter initial balance: ");
                            double initialBalance = scanner.nextDouble();
                            Account account = new ConcreteAccount(accountId, userName, initialBalance); // Instantiate ConcreteAccount
                            account.saveToDatabase();
                            System.out.println("Account created successfully!");
                            break;
                        case 2:
                            System.out.print("Enter account ID: ");
                            accountId = scanner.nextLine();
                            System.out.print("Enter deposit amount: ");
                            double depositAmount = scanner.nextDouble();
                            Account depositAccount = Account.getAccountById(accountId);
                            depositAccount.deposit(depositAmount);
                            System.out.println("Deposited successfully!");
                            break;
                        case 3:
                            System.out.print("Enter account ID: ");
                            accountId = scanner.nextLine();
                            System.out.print("Enter withdrawal amount: ");
                            double withdrawalAmount = scanner.nextDouble();
                            Account withdrawAccount = Account.getAccountById(accountId);
                            withdrawAccount.withdraw(withdrawalAmount);
                            System.out.println("Withdrawn successfully!");
                            break;
                        case 4:
                            System.out.print("Enter originating account ID: ");
                            String originatingAccountId = scanner.nextLine();
                            System.out.print("Enter resulting account ID: ");
                            String resultingAccountId = scanner.nextLine();
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine();  // Consume newline
                            System.out.print("Enter reason for the transfer: ");
                            String reason = scanner.nextLine();
                            Transaction transferTransaction = new Transaction(transferAmount, originatingAccountId, resultingAccountId, reason);
                            bank.performTransaction(transferTransaction);
                            System.out.println("Transferred successfully!");
                            break;
                        case 5:
                            System.out.print("Enter account ID: ");
                            accountId = scanner.nextLine();
                            Account balanceAccount = Account.getAccountById(accountId);
                            System.out.println("Account Balance: $" + String.format("%.2f", balanceAccount.getBalance()));
                            break;
                        case 6:
                            System.out.println("Bank Accounts:");
                            List<Account> accounts = Account.getAllAccounts();
                            for (Account acc : accounts) {
                                System.out.println(acc);
                            }
                            break;
                        case 7:
                            System.out.println("Total Transaction Fees: $" + String.format("%.2f", bank.getTotalTransactionFeeAmount()));
                            break;
                        case 8:
                            System.out.println("Total Transfer Amount: $" + String.format("%.2f", bank.getTotalTransferAmount()));
                            break;
                        case 9:
                            System.out.println("Exiting... Thank you for using the Bank System.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error accessing the database.");
        }
    }
}