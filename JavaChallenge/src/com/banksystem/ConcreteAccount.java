package com.banksystem;

import java.sql.SQLException;


public class ConcreteAccount extends Account {
    public ConcreteAccount(String accountId, String userName, double initialBalance) {
        super(accountId, userName, initialBalance);
    }

    @Override
    public void deposit(double amount) throws SQLException {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            updateBalanceInDatabase();
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    @Override
    public void withdraw(double amount) throws SQLException {
        if (amount > 0 && getBalance() >= amount) {
            setBalance(getBalance() - amount);
            updateBalanceInDatabase();
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid withdrawal amount");
        }
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }
}