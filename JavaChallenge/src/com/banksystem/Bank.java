package com.banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bank {
    private String bankName;
    private double totalTransactionFeeAmount;
    private double totalTransferAmount;
    private double flatFee;

    public Bank(String bankName, double flatFee) {
        this.bankName = bankName;
        this.flatFee = flatFee;
        this.totalTransactionFeeAmount = 0;
        this.totalTransferAmount = 0;
    }
    
    public void saveToDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "INSERT INTO Bank (bankName, totalTransactionFeeAmount, totalTransferAmount, flatFee) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, bankName);
                pstmt.setDouble(2, totalTransactionFeeAmount);
                pstmt.setDouble(3, totalTransferAmount);
                pstmt.setDouble(4, flatFee);
                pstmt.executeUpdate();
            }
        }
    }


    public static Bank getBank(String bankName) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "SELECT * FROM Bank WHERE bankName = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, bankName);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new Bank(
                            rs.getString("bankName"),
                            rs.getDouble("flatFee")
                        );
                    } else {
                        throw new IllegalArgumentException("Bank not found");
                    }
                }
            }
        }
    }


    public void updateTransactionAndTransferAmounts() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
            String sql = "UPDATE Bank SET totalTransactionFeeAmount = ?, totalTransferAmount = ? WHERE bankName = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, totalTransactionFeeAmount);
                pstmt.setDouble(2, totalTransferAmount);
                pstmt.setString(3, bankName);
                pstmt.executeUpdate();
            }
        }
    }


    public void performTransaction(Transaction transaction) throws SQLException {
        Account originatingAccount = Account.getAccountById(transaction.getOriginatingAccountId());
        Account resultingAccount = Account.getAccountById(transaction.getResultingAccountId());
        
        // Check if accounts exist
        if (originatingAccount != null && resultingAccount != null) {
            // Perform transaction
            double totalAmount = transaction.getAmount() + flatFee;
            if (originatingAccount.getBalance() >= totalAmount) {
                originatingAccount.withdraw(totalAmount); // Withdraw from originating account
                resultingAccount.deposit(transaction.getAmount()); // Deposit into resulting account
                
                // Update transaction and transfer amounts
                totalTransactionFeeAmount += flatFee;
                totalTransferAmount += transaction.getAmount();
                transaction.saveToDatabase();
                updateTransactionAndTransferAmounts();
                System.out.println("Transferred successfully!");
            } else {
                System.out.println("Insufficient balance in the originating account.");
            }
        } else {
            throw new IllegalArgumentException("Invalid accounts for transaction");
        }
    }


    public double getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    @Override
    public String toString() {
        return "Bank [name=" + bankName + ", totalTransactionFeeAmount=$" + String.format("%.2f", totalTransactionFeeAmount) + ", totalTransferAmount=$" + String.format("%.2f", totalTransferAmount) + "]";
    }
}
