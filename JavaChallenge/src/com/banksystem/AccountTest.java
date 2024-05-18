package com.banksystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        // Create a new Account object for each test case
        account = new ConcreteAccount("123456", "John Doe", 100.0);
    }

    @Test
    public void testDeposit() throws SQLException {
        // Arrange
        double initialBalance = account.getBalance();
        double depositAmount = 50.0;
        
        // Act
        account.deposit(depositAmount);
        
        // Assert
        assertEquals(initialBalance + depositAmount, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() throws SQLException {
        // Arrange
        double initialBalance = account.getBalance();
        double withdrawalAmount = 50.0;
        
        // Act
        account.withdraw(withdrawalAmount);
        
        // Assert
        assertEquals(initialBalance - withdrawalAmount, account.getBalance(), 0.001);
    }
}