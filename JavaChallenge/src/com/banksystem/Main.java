package com.banksystem;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String BANK_NAME = "BKT";
    private static final double FLAT_FEE = 10.0; 
    
    public static void main(String[] args) throws SQLException {        

        Scanner scanner = new Scanner(System.in);
        // Retrieve bank information
        Bank bank = null;
        try {
            bank = Bank.getBank(BANK_NAME);
        } catch (IllegalArgumentException e) {
            // Handle the case where the bank is not found
            System.out.println("Bank not found. Creating a new bank...");
        }

        if (bank == null) {
            // If bank does not exist, create a new one
            try {
                bank = new Bank(BANK_NAME, FLAT_FEE);
                bank.saveToDatabase();
                System.out.println("Bank created successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error creating the bank.");
                return;
            }
        } else {
            System.out.println("Bank already exists.");
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
                    case 4: // Transfer
                        System.out.print("Enter originating account ID: ");
                        String originatingAccountId = scanner.nextLine();
                        System.out.print("Enter resulting account ID: ");
                        String resultingAccountId = scanner.nextLine();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter reason for the transfer: ");
                        String reason = scanner.nextLine();

                        // Check if originating account has enough balance
                        Account originatingAccount = Account.getAccountById(originatingAccountId);
                        if (originatingAccount.getBalance() >= transferAmount) {
                            // Perform the transaction
                            Transaction transferTransaction = new Transaction(transferAmount, originatingAccountId, resultingAccountId, reason);
                            bank.performTransaction(transferTransaction);
                        } else {
                            System.out.println("Insufficient balance in the originating account.");
                        }
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
    }
}
