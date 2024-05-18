package com.banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountId;
    private String userName;
    protected double balance;

    public Account(String accountId, String userName, double initialBalance) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = initialBalance;
    }

    public abstract void deposit(double amount) throws SQLException;

    public abstract void withdraw(double amount) throws SQLException;

    public String getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public void saveToDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "INSERT INTO Account (accountId, userName, balance) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, accountId);
                pstmt.setString(2, userName);
                pstmt.setDouble(3, balance);
                pstmt.executeUpdate();
            }
        }
    }

    protected void updateBalanceInDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "UPDATE Account SET balance = ? WHERE accountId = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, balance);
                pstmt.setString(2, accountId);
                pstmt.executeUpdate();
            }
        }
    }

    public static Account getAccountById(String accountId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "SELECT * FROM Account WHERE accountId = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, accountId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new ConcreteAccount(
                            rs.getString("accountId"),
                            rs.getString("userName"),
                            rs.getDouble("balance")
                        );
                    } else {
                        throw new IllegalArgumentException("Account not found");
                    }
                }
            }
        }
    }

    public static List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "SELECT * FROM Account";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new ConcreteAccount(
                        rs.getString("accountId"),
                        rs.getString("userName"),
                        rs.getDouble("balance")
                    ));
                }
            }
        }
        return accounts;
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId + ", User: " + userName + ", Balance: $" + String.format("%.2f", balance);
    }
}