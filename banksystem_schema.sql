CREATE DATABASE banksystem;

USE banksystem;

CREATE TABLE Account (
    accountId VARCHAR(50) PRIMARY KEY,
    userName VARCHAR(100),
    balance DECIMAL(10, 2)
);

CREATE TABLE Transaction (
    transactionId INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2),
    originatingAccountId VARCHAR(50),
    resultingAccountId VARCHAR(50),
    reason VARCHAR(50),
    CONSTRAINT fk_originating_account FOREIGN KEY (originatingAccountId) REFERENCES Account(accountId),
    CONSTRAINT fk_resulting_account FOREIGN KEY (resultingAccountId) REFERENCES Account(accountId)
);

 
select * from bank;

select * from account;

select * from transaction;
